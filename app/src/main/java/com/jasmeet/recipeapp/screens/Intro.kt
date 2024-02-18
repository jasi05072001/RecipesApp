package com.jasmeet.recipeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.jasmeet.recipeapp.R
import com.jasmeet.recipeapp.appComponents.HorizontalSpacer
import com.jasmeet.recipeapp.appComponents.VerticalSpacer
import com.jasmeet.recipeapp.ui.theme.poppins

@Composable
fun IntroScreen(navController: NavHostController) {

    Box(
        Modifier
            .fillMaxSize()
    ) {
        IntroImage()
        HeaderLayout()
        BottomLayout(
            modifier = Modifier.align(Alignment.BottomCenter),
            navController = navController
        )

    }

}

@Composable
private fun BottomLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    Column(
        modifier
            .padding(bottom = 35.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Start\nCooking",
            style = TextStyle(
                fontSize = 32.sp,
                lineHeight = 35.sp,
                fontFamily = poppins,
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),

                textAlign = TextAlign.Center,
            )
        )
        VerticalSpacer(5.dp)
        Text(
            text = "Simple way to find Tasty Recipe",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = poppins,
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),

                textAlign = TextAlign.Center,
            )
        )

        VerticalSpacer(35.dp)

        Button(
            onClick = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(Screens.Intro.route, inclusive = true)
                    .build()
                navController.navigate(Screens.SignIn.route, navOptions)
            },
            modifier = Modifier.fillMaxWidth(0.75f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF129575)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Start Cooking",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    ),
                )
                HorizontalSpacer(5.dp)
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = Color(0xFFFFFFFF),
                    modifier = Modifier.size(24.dp)
                )
            }

        }

    }
}

@Composable
private fun HeaderLayout() {
    Column(
        Modifier
            .padding(top = 38.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_crown),
            contentDescription = null,
            modifier = Modifier
                .statusBarsPadding()
                .size(64.dp)
        )
        Text(
            text = "100k+ Premium Recipes",
            modifier = Modifier.padding(top = 16.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = poppins,
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),

                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun IntroImage() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.img_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier= Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.8f),
                        )
                    )
                )
        ) {}



    }
}