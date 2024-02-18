package com.jasmeet.recipeapp.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.jasmeet.recipeapp.R
import com.jasmeet.recipeapp.appComponents.VerticalSpacer
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavController) {


    val context = LocalContext.current

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

    val hazeState = remember { HazeState() }

    val scrollState = rememberLazyListState()


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Home")
            })

        },
        bottomBar = {
            AnimatedVisibility(
                visible = !scrollState.isScrollInProgress,
                enter = fadeIn()+ scaleIn(),
                exit = fadeOut()+ scaleOut()
            ) {
                GlassmorphicBottomNavigation(hazeState = hazeState)
            }
        }
    ) {
//        Column(
//            Modifier
//                .fillMaxSize()
//                .background(Color.White)
//                .padding(it)
//        ) {
//            Text(
//                text = "Home",
//                modifier = Modifier
//
//            )
//            VerticalSpacer(height = 15.dp)
//            Button(onClick = {
//                logout(googleSignInClient, onSuccess = {
//                    val navOptions = NavOptions.Builder()
//                        .setPopUpTo(Screens.Home.route, inclusive = true)
//                        .build()
//                    navHostController.navigate(Screens.SignIn.route, navOptions)
//                },
//                    onFailure = {error ->
//                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
//                    }
//                )
//            }) {
//                Text(text = "Logout")
//            }
//
//        }

        LazyColumn(
            Modifier
                .haze(
                    hazeState,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    tint = Color.Black.copy(alpha = .2f),
                    blurRadius = 30.dp,
                )
                .fillMaxSize(),
            contentPadding = it,
            state = scrollState,
        ) {
            items(50) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.DarkGray, RoundedCornerShape(12.dp))
                        .border(
                            width = Dp.Hairline,
                            color = Color.White.copy(alpha = .5f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                ) {
                  ElevatedCard(onClick = { /*TODO*/ }) {
                      Text(text ="Item it")
                  }
                }
            }
        }
    }
}

fun logout(
    googleSignInClient: GoogleSignInClient,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    FirebaseAuth.getInstance().signOut()
    googleSignInClient.signOut().addOnSuccessListener {
        onSuccess()
    }.addOnFailureListener {
        onFailure(it.message.toString())
    }
}