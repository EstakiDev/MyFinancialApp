package dev.estaki.myFinancialApp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.estaki.myFinancialApp.R

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {

    val splashScreenLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.anim_splash_money
        )
    )

    val splashScreenProgress by animateLottieCompositionAsState(
        composition = splashScreenLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    LottieAnimation(
        composition = splashScreenLottieComposition,
        progress = splashScreenProgress,
        modifier = modifier
    )



}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}