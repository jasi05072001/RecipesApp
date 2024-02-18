package com.jasmeet.recipeapp.screens

sealed class Screens(val route : String) {
    data object Splash : Screens("splash")
    data object Intro : Screens("intro")
    data object SignIn : Screens("sign_in")
    data object SignUp : Screens("sign_up")
    data object ForgotPassword :Screens("forgot_password")
    data object Home : Screens("home")

}