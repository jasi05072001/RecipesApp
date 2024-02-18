package com.jasmeet.recipeapp.navigation

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jasmeet.recipeapp.screens.ForgotPasswordScreen
import com.jasmeet.recipeapp.screens.HomeScreen
import com.jasmeet.recipeapp.screens.IntroScreen
import com.jasmeet.recipeapp.screens.Screens
import com.jasmeet.recipeapp.screens.SignIn
import com.jasmeet.recipeapp.screens.SignUpScreen
import com.jasmeet.recipeapp.screens.SplashScreen

@Composable
fun MainNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Splash.route
    ) {
        composable(
            route = Screens.Splash.route,
            exitTransition = {
                exitTransition()
            }
        ) {
            SplashScreen(navController = navHostController)
        }
        composable(
            route = Screens.Intro.route,
            enterTransition = {
                enterTransition()
            },
            exitTransition = {
                exitTransition()
            },
        ) {
            IntroScreen(navController = navHostController)
        }
        composable(
            route = Screens.SignIn.route,
            enterTransition = {
                enterTransition()
            },
            exitTransition = {
                exitTransition()
            }
        ) {
            SignIn(navController = navHostController)
        }
        composable(
            route = Screens.SignUp.route,
            enterTransition = {
                enterTransition()
            },
            exitTransition = {
                exitTransition()
            }
        ) {
            SignUpScreen(navController = navHostController)
        }

        composable(
            route = Screens.ForgotPassword.route,
            enterTransition = {
                enterTransition()
            },
            exitTransition = {
                exitTransition()
            }
        ) {
            ForgotPasswordScreen(navHostController = navHostController)
        }

        composable(
            route = Screens.Home.route,
            enterTransition = {
                enterTransition()
            },
            exitTransition = {
                exitTransition()
            }
        ) {
            HomeScreen(navHostController = navHostController)
        }
    }

}

private fun exitTransition() = slideOutHorizontally(
    targetOffsetX = { -1000 },
    animationSpec = tween(
        500,
        easing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)
    )
)


private fun enterTransition() = slideInHorizontally(
    initialOffsetX = { 1000 },
    animationSpec = tween(
        1000, easing = CubicBezierEasing(0.2f, 0.8f, 0.2f, 1f)
    )
)