package dev.estaki.myFinancialApp.presentation.splash

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.valentinilk.shimmer.shimmer
import dev.estaki.myFinancialApp.R
import dev.estaki.myFinancialApp.isPermissionsGranted
import dev.estaki.myFinancialApp.presentation.actions.SplashScreenActions
import dev.estaki.myFinancialApp.presentation.states.MyTopAppBarState
import dev.estaki.myFinancialApp.presentation.states.SplashScreenState
import dev.estaki.ui_utils.components.PermissionBottomSheet
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnDarkTheme
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnLiteTheme
import dev.estaki.ui_utils.ui.theme.coolakFaNumFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashScreenViewModel = hiltViewModel(),
    navController: NavHostController,
    onComposing: (MyTopAppBarState) -> Unit,
) {
    val splashScreenState by viewModel.splashScreenState.collectAsState()
    LaunchedEffect(true) {
        onComposing.invoke(MyTopAppBarState())
    }

    SplashScreenUi(
        modifier = modifier,
        navController = navController,
        onAction = viewModel::onAction,
        state = splashScreenState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreenUi(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onAction: (SplashScreenActions) -> Unit,
    state: SplashScreenState
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    LaunchedEffect(false) {
        if (isPermissionsGranted(context)) {
            onAction.invoke(SplashScreenActions.ExtractSmsFromContentResolver(context.contentResolver))
        } else {
            onAction.invoke(SplashScreenActions.GetPermissions)
        }
    }
    LaunchedEffect(state.isFinishedAndGoToMainScreen,state.isLoading) {
        if (state.isFinishedAndGoToMainScreen) {
            navController.navigate("MainScreen") {
                popUpTo("SplashScreen") {
                    inclusive = true
                }
            }
        }
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            if (it.all { map -> map.value == true }) {
                onAction.invoke(SplashScreenActions.ExtractSmsFromContentResolver(context.contentResolver))
            } else {

            }
        }
    )
    val alpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(true) {
        alpha.animateTo(1f, animationSpec = tween(1000))
    }

    if (state.mustGetPermissions) {
        PermissionBottomSheet(
            modifier = Modifier,
            modalBottomSheetState,
            onBtnGetPermissionClicked = {
                requestPermissionLauncher.launch(
                    arrayOf(
                        android.Manifest.permission.READ_SMS,
                        android.Manifest.permission.RECEIVE_SMS,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    )
                )
            }) {

        }
    } else {
        LaunchedEffect(true) {
            if (modalBottomSheetState.isVisible)
                modalBottomSheetState.hide()
        }
    }

    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SplashScreenLottieIcon(
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .alpha(alpha.value)

            )
            Text(
                text = "سلام رفیق 👋🏻",
                fontFamily = coolakFaNumFontFamily,
                fontSize = 18.sp,
                modifier = Modifier
                    .alpha(alpha.value)
                    .shimmer()

            )
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "به  اپلیکیشن ",
                    modifier = Modifier
                        .padding(12.dp)
                        .alpha(alpha.value)
                        .shimmer(),
                    fontSize = 15.sp,
                    fontFamily = coolakFaNumFontFamily,
                    textAlign = TextAlign.Center,
                    style = TextStyle(textDirection = TextDirection.Rtl)
                )
                Text(
                    text = "مانیفای",
                    modifier = Modifier
                        .alpha(alpha.value)
                        .shimmer(),
                    fontSize = 32.sp,
                    fontFamily = coolakFaNumFontFamily,
                    textAlign = TextAlign.Center,
                    style = TextStyle(textDirection = TextDirection.Rtl)
                )
                Text(
                    text = " خوش اومدی...",
                    modifier = Modifier
                        .padding(12.dp)
                        .alpha(alpha.value)
                        .shimmer(),
                    fontSize = 15.sp,
                    fontFamily = coolakFaNumFontFamily,
                    textAlign = TextAlign.Center,
                    style = TextStyle(textDirection = TextDirection.Rtl)
                )
            }


        }

        BallPulseProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(0.dp, 32.dp),
            color = if (isSystemInDarkTheme()) ColorTextGrayOnDarkTheme else ColorTextGrayOnLiteTheme,
            animationDuration = 800,
            animationDelay = 200,
            startDelay = 0,
            ballCount = 3,
            maxBallDiameter = 7.dp

        )

    }
}


@Composable
fun SplashScreenLottieIcon(modifier: Modifier = Modifier) {

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
    SplashScreenLottieIcon()
}