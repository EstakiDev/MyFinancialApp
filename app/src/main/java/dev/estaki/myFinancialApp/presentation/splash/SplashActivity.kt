package dev.estaki.myFinancialApp.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.valentinilk.shimmer.shimmer
import dagger.hilt.android.AndroidEntryPoint
import dev.estaki.myFinancialApp.isPermissionsGranted
import dev.estaki.myFinancialApp.presentation.ViewState
import dev.estaki.myFinancialApp.presentation.main.MainActivity
import dev.estaki.myFinancialApp.presentation.main.MainViewModel
import dev.estaki.myFinancialApp.ui.theme.FinancialTheme
import dev.estaki.ui_utils.components.PermissionBottomSheet
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnDarkTheme
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnLiteTheme
import dev.estaki.ui_utils.ui.theme.Pink40
import dev.estaki.ui_utils.ui.theme.coolakFaNumFontFamily
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels<MainViewModel>()
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            lifecycleScope.launch {
                if (it.all { map -> map.value == true }) {
                    viewModel.viewState.emit(ViewState.SUCCESS_IN_PERMISSION)
                } else {
                    viewModel.viewState.emit(ViewState.FAULT_IN_PERMISSION)
                }
            }
        }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllCategory()

        setContent {
            FinancialTheme {
                val viewState by viewModel.viewState.collectAsState()
                val scope = rememberCoroutineScope()
                val snackBarHostState = remember {
                    SnackbarHostState()
                }
                val modalBottomSheetState = rememberModalBottomSheetState()

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                    Scaffold(snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState) {
                            Snackbar(snackbarData = it, containerColor = Pink40)
                        }
                    }) {
                        val alpha = remember {
                            Animatable(0f)
                        }



                        LaunchedEffect(key1 = true) {
                            alpha.animateTo(1f, animationSpec = tween(2000))
                        }


                        Box {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                SplashScreen(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                        .alpha(alpha.value)
                                        .padding(it)

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
                                        text = "به اپلیکیشن ",
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
                        if (!isPermissionsGranted(this)) {

                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                PermissionBottomSheet(
                                    modifier = Modifier,
                                    modalBottomSheetState,
                                    onBtnGetPermissionClicked = {
                                        requestPermissionLauncher.launch(arrayOf(
                                            android.Manifest.permission.READ_SMS,
                                            android.Manifest.permission.RECEIVE_SMS,
                                            android.Manifest.permission.POST_NOTIFICATIONS
                                        ))
                                    }) {
                                    lifecycleScope.launch {
                                        viewModel.viewState.emit(ViewState.FAULT_IN_PERMISSION)
                                    }
                                }
                            }

                        } else {
                            LaunchedEffect(key1 = true) {
                                Timber.d("Permission granted read sms started")
                                viewModel.readSms(contentResolver)
                            }
                        }



                        when (viewState) {
                            ViewState.FAULT_IN_PERMISSION -> {
                                CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Rtl) {
                                    LaunchedEffect(key1 = "hide") {
                                        modalBottomSheetState.hide()
                                        scope.launch {
                                            snackBarHostState.showSnackbar(message = "متاسفانه شما دسترسی مورد نیاز رو تایید نکردید... 😯")
                                            finishAndGotoMain()
                                        }
                                    }
                                }

                            }

                            ViewState.LOADING -> {}
                            ViewState.FAULT -> {}
                            ViewState.FINISH_SPLASH_ACTIVITY -> {
                                finishAndGotoMain()
                            }

                            ViewState.SUCCESS_IN_PERMISSION -> {
                                LaunchedEffect(key1 = "DismissBottomSheet") {
                                    modalBottomSheetState.hide()
                                }
                            }

                            else -> {}
                        }
                    }

                }
            }

        }
    }


    private fun finishAndGotoMain() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this@SplashActivity.finish()
    }


}