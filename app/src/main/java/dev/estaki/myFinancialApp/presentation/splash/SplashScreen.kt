package dev.estaki.myFinancialApp.presentation.splash

import android.Manifest
import android.app.Activity
import androidx.activity.compose.LocalActivity
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.valentinilk.shimmer.shimmer
import dev.estaki.domain.error.MyCustomSnackBarType
import dev.estaki.myFinancialApp.R
import dev.estaki.myFinancialApp.isPermissionsGranted
import dev.estaki.myFinancialApp.presentation.actions.SplashScreenActions
import dev.estaki.myFinancialApp.presentation.states.MyTopAppBarState
import dev.estaki.myFinancialApp.presentation.states.SplashScreenState
import dev.estaki.ui_utils.SnackBarAction
import dev.estaki.ui_utils.SnackBarController
import dev.estaki.ui_utils.SnackBarEvent
import dev.estaki.ui_utils.components.MyAlertDialog
import dev.estaki.ui_utils.components.PermissionBottomSheet
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnDarkTheme
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnLiteTheme
import dev.estaki.ui_utils.ui.theme.coolakFaNumFontFamily
import dev.estaki.ui_utils.utils.showAppSettings
import kotlinx.coroutines.launch


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
    val permissions = arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.POST_NOTIFICATIONS
    )
    val modalBottomSheetState = rememberModalBottomSheetState()
    val context = LocalContext.current
    val activity = LocalActivity.current
    var showManyFailedAttemptPermissions by remember { mutableStateOf(false) }
    var mustGetPermissions by remember { mutableStateOf(false) }
    var showDenyPermissions by remember { mutableStateOf(false) }
    var numberOfAttempts by remember { mutableIntStateOf(0) }

    LaunchedEffect(false) {
        if (isPermissionsGranted(context)) {
            onAction.invoke(SplashScreenActions.ExtractSmsFromContentResolver(context.contentResolver))
        } else {
//            onAction.invoke(SplashScreenActions.GetPermissions)
            mustGetPermissions = true
        }
    }
    LaunchedEffect(state.isFinishedAndGoToMainScreen, state.isLoading) {
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
            mustGetPermissions = false
            if (it.all { map -> map.value == true }) {
                onAction.invoke(SplashScreenActions.ExtractSmsFromContentResolver(context.contentResolver))
            } else {
                checkShouldShowRequestPermissionRationale(activity!!, permissions) {
                    if (it || numberOfAttempts >= 2) {
                        showManyFailedAttemptPermissions = true
                    } else {
                        numberOfAttempts++
                        showDenyPermissions = true
                    }
                }

            }
        }
    )
    val alpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(true) {
        alpha.animateTo(1f, animationSpec = tween(1000))
    }


    if (mustGetPermissions) {
        PermissionBottomSheet(
            modifier = Modifier,
            modalBottomSheetState,
            onBtnGetPermissionClicked = {
                checkShouldShowRequestPermissionRationale(activity!!, permissions) {
                    if (it) {
                        mustGetPermissions = false
                        showManyFailedAttemptPermissions = true
                    } else {
                        requestPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.READ_SMS,
                                Manifest.permission.RECEIVE_SMS,
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                        )
                    }
                }
            },
            onDismissRequest = {
                mustGetPermissions = false
                showDenyPermissions = true
            }
        )
    } else {
        LaunchedEffect(true) {
            if (modalBottomSheetState.isVisible)
                modalBottomSheetState.hide()
        }
    }


    if (showDenyPermissions) {
        MyAlertDialog(
            onDismissRequest = {
                showDenyPermissions = true
            },
            onConfirmation = {
//                onAction.invoke(SplashScreenActions.GetPermissions)
                showDenyPermissions = false
                mustGetPermissions = true
            },
            "هشدار",
            "متاسفانه شما دسترسی های اجباری مورد نیاز مانیفای رو تایید نکردید، لطفا دسترسی ها را تایید کنید.",
            icon = Icons.Rounded.Warning,
            hasDismissBtn = false,
            confirmButtonText = "بزن بریم"
        )
    }

    if (showManyFailedAttemptPermissions) {
        MyAlertDialog(
            onDismissRequest = {
                showManyFailedAttemptPermissions = false
            },
            onConfirmation = {
                activity?.showAppSettings()
                mustGetPermissions = false
                showManyFailedAttemptPermissions = false
            },
            "هشدار",
            "متاسفانه بدلیل تلاش بیش از حد برای دریافت دسترسی ها، دریافت به صورت مستقیم امان پذیر نیست، لطفا با زدن دکنه زیر به تنظیمات اپلیکیشن رفته و از آن قسمت دسترسی های مورد نیاز اپ را تایید کنید.",
            icon = Icons.Rounded.Warning,
            hasDismissBtn = false,
            confirmButtonText = "بزن بریم"
        )
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

private fun checkShouldShowRequestPermissionRationale(
    activity: Activity,
    listOfPermissions: Array<String>,
    onResult: (shouldShowRequestPermissionRationale: Boolean) -> Unit
) {
    var shouldShowRequestPermissionRationale = false
    listOfPermissions.forEach {
        shouldShowRequestPermissionRationale =
            shouldShowRequestPermissionRationale(activity, it)
        if (shouldShowRequestPermissionRationale)
            return@forEach
    }
    onResult.invoke(shouldShowRequestPermissionRationale)
}