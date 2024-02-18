package com.jasmeet.recipeapp.viewModel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.jasmeet.recipeapp.data.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db : FirebaseFirestore
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthResult?>(null)
    val authState: StateFlow<AuthResult?> = _authState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val debounceTimeMillis = 5000L
    private var lastErrorShownTime = 0L

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val state = MutableStateFlow<Boolean>(false)
    val stateFlow: StateFlow<Boolean> = state


    init {
        viewModelScope.launch {
            while (true) {
                delay(debounceTimeMillis)
                setErrorMessage(null)
            }
        }
    }

    fun setErrorMessage(errorMessage: String?) {
        val currentTimeMillis = System.currentTimeMillis()
        if (errorMessage != null && (currentTimeMillis - lastErrorShownTime) >= debounceTimeMillis) {
            _errorState.value = errorMessage
            lastErrorShownTime = currentTimeMillis

        } else if (errorMessage == null) {
            _errorState.value = null
            lastErrorShownTime = 0L
        }
    }

    fun signInWithYahoo(context: Context) {

        val provider = OAuthProvider.newBuilder("yahoo.com")
            .addCustomParameter("prompt", "login")
            .addCustomParameter("language", "en")
            .setScopes(
                listOf(
                    "email",
                    "profile"
                )
            )
            .build()

        val pendingTask = auth.pendingAuthResult

        if (pendingTask != null) {
            pendingTask.addOnSuccessListener {
                _authState.value = it
            }
                .addOnFailureListener {
                    setErrorMessage(it.message)
                }
        }
        else {
            _isLoading.value = true
            auth.startActivityForSignInWithProvider(context as Activity, provider)
                .addOnSuccessListener {
                    _authState.value = it
                    db.collection("users").document(it.user?.uid.toString()).set(
                        UserInfo(
                            name = it.user?.displayName,
                            email = it.user?.email,
                            uid = it.user?.uid,
                            imgUrl = it.user?.photoUrl.toString()
                        )
                    ).addOnSuccessListener {
                        state.value = true
                        _isLoading.value = false

                    }.addOnFailureListener { exception ->
                        state.value = false
                        _isLoading.value = false
                        setErrorMessage(exception.message)
                    }
                }
                .addOnFailureListener {
                    setErrorMessage(it.message)
                    _isLoading.value = false
                    state.value = false
                }
        }
    }

    fun saveDataToFireStore(it: AuthResult) {
        db.collection("users").document(it.user?.uid.toString()).set(
            UserInfo(
                name = it.user?.displayName,
                email = it.user?.email,
                uid = it.user?.uid,
                imgUrl = it.user?.photoUrl.toString()
            )
        ).addOnSuccessListener {
            state.value = true
            _isLoading.value = false

        }.addOnFailureListener { exception ->
            state.value = false
            _isLoading.value = false
            setErrorMessage(exception.message)
        }
    }

    fun signInEmailPassword(email: String, password: String) {
        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.value = it
                _isLoading.value = false
                state.value = true
            }
            .addOnFailureListener {
                setErrorMessage(it.message)
                _isLoading.value = false
                state.value = false
            }
    }
}
