package com.jasmeet.recipeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jasmeet.recipeapp.R
import com.jasmeet.recipeapp.appComponents.HorizontalSpacer
import com.jasmeet.recipeapp.appComponents.InputField
import com.jasmeet.recipeapp.appComponents.Loader
import com.jasmeet.recipeapp.appComponents.PasswordFieldComponent
import com.jasmeet.recipeapp.appComponents.VerticalSpacer
import com.jasmeet.recipeapp.constants.LoadingAnimationType
import com.jasmeet.recipeapp.googleSignIn.rememberFirebaseAuthLauncher
import com.jasmeet.recipeapp.ui.theme.poppins
import com.jasmeet.recipeapp.utils.Utils
import com.jasmeet.recipeapp.viewModel.SignInViewModel
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignIn(
    navController: NavHostController,
    signInViewModel: SignInViewModel = hiltViewModel()
) {

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    val debouncedMessage by signInViewModel.errorState.collectAsState()
    val isLoading by signInViewModel.isLoading.collectAsState()

    val googleSignInLoading = rememberSaveable { mutableStateOf(false) }

    val token = stringResource(R.string.default_web_client_id)
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .requestProfile()
            .build()
    }

    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, gso)
    }

    val user = rememberSaveable { mutableStateOf(Firebase.auth.currentUser) }

    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = {result ->
            user.value = result.user
            signInViewModel.saveDataToFireStore(result)
            googleSignInLoading.value = false
            val navOptions = NavOptions.Builder()
                .setPopUpTo(Screens.SignIn.route, inclusive = true)
                .build()
            navController.navigate(Screens.Home.route, navOptions)
        },
        onAuthError = {
            user.value = null
            googleSignInLoading.value = false
            signInViewModel.setErrorMessage(it)

        }
    )


    if (debouncedMessage != null && debouncedMessage?.isNotEmpty() == true) {
        SweetError(
            message = debouncedMessage ?: "Something went wrong !",
            padding = PaddingValues(vertical = 15.dp, horizontal = 10.dp)
        )

    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 30.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Hello,",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                )
            )
            Text(
                text = "Welcome Back!",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF121212),
                )
            )

            VerticalSpacer(height = 30.dp)

            Text(
                text = "Email",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF121212),
                )
            )
            VerticalSpacer(height = 5.dp)

            InputField(
                value = email.value,
                onValueChange = {
                    email.value = it
                },
                hint = "Enter Email",
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpacer(height = 15.dp)

            Text(
                text = "Password",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF121212),
                )
            )

            VerticalSpacer(height = 5.dp)

            PasswordFieldComponent(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                hint = "Enter Password",
                modifier = Modifier.fillMaxWidth(),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()

                        if (Utils.validateEmail(email.value) && Utils.validatePassword(password.value)) {
                            signInViewModel.signInEmailPassword(email.value, password.value)
                            initiateLoginFlow(coroutine, signInViewModel, navController)
                        }
                        else{
                            signInViewModel.setErrorMessage("Please fill all the fields!!")
                        }
                    }
                )
            )

            VerticalSpacer(height = 12.dp)

            TextButton(
                onClick = {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(Screens.SignIn.route, inclusive = true)
                        .build()
                    navController.navigate(Screens.ForgotPassword.route, navOptions)
                }
            ) {
                Text(
                    text = "Forgot Password?",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFF9C00),
                    )
                )

            }

            VerticalSpacer(height = 20.dp)

            Button(
                enabled = Utils.validateEmail(email.value) && Utils.validatePassword(password.value),
                onClick = {
                    signInViewModel.signInEmailPassword(email.value, password.value)
                    initiateLoginFlow(coroutine, signInViewModel, navController)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF129575)
                ),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.buttonElevation(
                    disabledElevation = 0.dp,
                    defaultElevation = 10.dp,
                    pressedElevation = 15.dp
                )
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sign In",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        ),
                    )
                    HorizontalSpacer(10.dp)
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_forward),
                        contentDescription = null,
                        tint = Color(0xFFFFFFFF),
                        modifier = Modifier.size(24.dp)
                    )
                }

            }

            VerticalSpacer(height = 15.dp)

            Row(
                Modifier
                    .fillMaxWidth(0.65f)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Divider(modifier = Modifier.weight(1f))
                Text(
                    text = "Or Sign in With",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF999999),
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Divider(modifier = Modifier.weight(1f))

            }

            VerticalSpacer(height = 15.dp)

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                ElevatedCard(
                    onClick = {
                        googleSignInLoading.value = true
                        launcher.launch(googleSignInClient.signInIntent)
                    },
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 15.dp,
                        pressedElevation = 25.dp,
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(40.dp)
                    )

                }
                HorizontalSpacer(width = 25.dp)

                ElevatedCard(
                    onClick = {
                        signInViewModel.signInWithYahoo(context)
                        initiateLoginFlow(coroutine, signInViewModel, navController)

                    },
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 15.dp,
                        pressedElevation = 25.dp,
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_yahoo),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                            .size(40.dp)
                    )

                }

            }

            VerticalSpacer(height = 35.dp)
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don’t have an account?",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                    )
                )
                Text(
                    text = "Sign up",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight(600),
                        color = Color(0xffff9c00),
                        textDecoration = TextDecoration.Underline,
                    ),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(5.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            val navOptions = NavOptions.Builder()
                                .setPopUpTo(Screens.SignIn.route, inclusive = true)
                                .build()
                            navController.navigate(Screens.SignUp.route, navOptions)

                        }
                )

            }
        }
        if (isLoading || googleSignInLoading.value) {
            Loader(
                loadingText = "Signing In...",
                animationType = LoadingAnimationType.ALPHA

            )
        }
    }
}

private fun initiateLoginFlow(
    coroutine: CoroutineScope,
    signInViewModel: SignInViewModel,
    navController: NavHostController
) {
    coroutine.launch {
        signInViewModel.stateFlow.collect {
            if (it) {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(Screens.SignIn.route, inclusive = true)
                    .build()
                navController.navigate(Screens.Home.route, navOptions)
            }
        }

    }
}


@Preview
@Composable
private fun SignInScreen() {
    val context = LocalContext.current
    SignIn(navController = NavHostController(context))
}




