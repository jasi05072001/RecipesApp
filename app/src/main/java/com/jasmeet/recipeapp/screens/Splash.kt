package com.jasmeet.recipeapp.screens

import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.jasmeet.recipeapp.R
import com.jasmeet.recipeapp.appComponents.VerticalSpacer
import com.jasmeet.recipeapp.ui.theme.poppins
import com.jasmeet.recipeapp.viewModel.SplashViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    val isUserLoggedIn = splashViewModel.isUserLoggedIn

    val scale = remember {
        Animatable(0f)
    }
    val textAnimation = remember {
        Animatable(0f)
    }

    val color = remember {
        Animatable(Color.Gray)
    }

    val progressColor = remember {
        Animatable(Color(0xffFFFF00))
    }

    LaunchedEffect(Unit) {

        color.animateTo(
            Color.Gray,
            animationSpec = tween(
                1000,
                easing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)
            )
        )

        color.animateTo(
            Color(0xff2E8B57).copy(alpha = 0.8f),
            animationSpec = tween(
                1000,
                easing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)
            )
        )


        progressColor.animateTo(
            Color(0xffFFFF00),
            animationSpec = tween(
                1000,
                easing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)
            )
        )

        progressColor.animateTo(
            Color(0xFFFF9800).copy(alpha = 0.8f),
            animationSpec = tween(
                1000,
                easing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)
            )
        )


    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.5f,
            animationSpec = tween(
                durationMillis = 1800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        textAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1400,
                easing = {
                    LinearInterpolator().getInterpolation(it)
                }
            )
        )
        splashViewModel.checkForActiveSession()
        delay(1850)

        if (isUserLoggedIn.value == true){
            val navOptions = NavOptions.Builder()
                .setPopUpTo(Screens.Splash.route, inclusive = true)
                .build()
            navController.navigate(Screens.Home.route, navOptions)
        }

        else{
            val navOptions = NavOptions.Builder()
                .setPopUpTo(Screens.Splash.route, inclusive = true)
                .build()
            navController.navigate(Screens.Intro.route, navOptions)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color.value)
    ) {
        Column(
            Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LogoImage(scale)
            VerticalSpacer(15.dp)
            AppTitle(textAnimation)

        }
        CircularProgressIndicator(
            Modifier
                .navigationBarsPadding()
                .padding(bottom = 15.dp)
                .align(Alignment.BottomCenter),
            color = progressColor.value
        )
    }
}

@Composable
private fun AppTitle(textAnimation: Animatable<Float, AnimationVector1D>) {
    Text(
        text = stringResource(id = R.string.app_name),
        style = TextStyle(
            color = Color.White,
            fontSize = 25.sp,
            fontFamily = poppins,
            fontWeight = FontWeight(400),
            fontStyle = FontStyle.Italic
        ),
        modifier = Modifier
            .padding(15.dp)
            .scale(textAnimation.value)
    )
}

@Composable
private fun LogoImage(scale: Animatable<Float, AnimationVector1D>) {
    Surface(
        color = Color((0xff2E8B57)),
        shape = CircleShape,
        shadowElevation = 10.dp,
        tonalElevation = 10.dp,
        modifier = Modifier.scale(scale.value)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_crown),
            contentDescription = "Logo",
            modifier = Modifier
                .size(75.dp)
                .padding(10.dp)
        )
    }
}