package com.jasmeet.recipeapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

@Composable
fun ForgotPasswordScreen(navHostController: NavHostController) {

    Column {
        Button(onClick = {

            val navOptions = NavOptions.Builder()
                .setPopUpTo(Screens.SignUp.route, inclusive = true)
                .build()
            navHostController.navigate(Screens.SignIn.route, navOptions)

        }, modifier = Modifier.statusBarsPadding().padding(15.dp)) {
            Text(text = "Dummy")
        }
    }

}