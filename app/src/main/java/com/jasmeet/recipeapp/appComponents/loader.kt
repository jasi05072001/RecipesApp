package com.jasmeet.recipeapp.appComponents

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jasmeet.recipeapp.R
import com.jasmeet.recipeapp.constants.LoadingAnimationType
import com.jasmeet.recipeapp.ui.theme.poppins


@Composable
fun Loader(
    loadingText : String = "Loading...",
    animationType : LoadingAnimationType = LoadingAnimationType.BOUNCE
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loader),
        cacheKey = "loader"
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    val textAlpha by animateFloatAsState(
        targetValue = if (progress < 0.5f) 1f else 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val scale by animateDpAsState(
        targetValue = if (progress < 0.5f) 1.2.dp else 0.8.dp,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )



    Dialog(onDismissRequest = { /*TODO*/ }) {
        Box(
            Modifier
                .clip(MaterialTheme.shapes.large)
                .fillMaxWidth(0.65f)
                .wrapContentSize()
                .background(Color.White, MaterialTheme.shapes.large)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(140.dp),
                    renderMode = RenderMode.HARDWARE,

                    )

                Text(
                    text = loadingText,
                    textAlign = TextAlign.Center,
                    fontFamily = poppins,
                    color = Color.Black,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .then(if(LoadingAnimationType.BOUNCE == animationType) Modifier
                            .scale(scale.value) else Modifier.alpha(textAlpha))
                        .padding(top = 6.dp, bottom = 15.dp)
                )

            }
        }
    }
}

