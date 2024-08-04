package dev.estaki.myFinancialApp

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.valentinilk.shimmer.shimmer
import dagger.hilt.android.AndroidEntryPoint
import dev.estaki.myFinancialApp.presentation.SplashScreen
import dev.estaki.myFinancialApp.ui.theme.ColorTextGrayOnDarkTheme
import dev.estaki.myFinancialApp.ui.theme.ColorTextGrayOnLiteTheme
import dev.estaki.myFinancialApp.ui.theme.FinancialTheme
import dev.estaki.myFinancialApp.ui.theme.Pink40
import dev.estaki.myFinancialApp.ui.theme.ariaFaNumFontFamily
import dev.estaki.myFinancialApp.ui.theme.coolakFaNumFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    lateinit var modalBottomSheetState: SheetState


    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                lifecycleScope.launch {
                    viewModel.viewState.value = ViewState.SUCCESS_IN_PERMISSION
                    readSms(contentResolver, viewModel)
                }
            } else {
                viewModel.viewState.value = ViewState.FAULT_IN_PERMISSION
            }
        }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FinancialTheme {
                val viewState = viewModel.viewState.observeAsState()
                val scope = rememberCoroutineScope()
                val snackBarHostState = remember {
                    SnackbarHostState()
                }
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

                    Scaffold(snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState) {
                            Snackbar(snackbarData = it, containerColor = Pink40)
                        }
                    }) {
                        val alpha = remember {
                            Animatable(0f)
                        }

                        modalBottomSheetState = rememberModalBottomSheetState()
//                    if (checkPermissions(this)) {
//                        requestPermissionLauncher.launch(Manifest.permission.READ_SMS)
//                    }
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
                                Text(
                                    text = "به اپلیکیشن مدیریت خودکار دخل و خرج با استفاده از پیامک های بانکی خوش اومدی...",
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .alpha(alpha.value)
                                        .shimmer(),
                                    fontSize = 18.sp,
                                    fontFamily = coolakFaNumFontFamily,
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(textDirection = TextDirection.Rtl)
                                )

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

                                ModalBottomSheet(
                                    onDismissRequest = {
                                        viewModel.viewState.value = ViewState.FAULT_IN_PERMISSION
                                    },
                                    sheetState = modalBottomSheetState
                                ) {

                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(12.dp),
                                            text = "سلام رفیق\n اپلیکیشن برای این که بتونه پیامک تراکنش های بانکی تو رو بهت نشون بده نیاز داره که تو این دسترسی رو تایید کنی.",
                                            fontFamily = ariaFaNumFontFamily,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                        Button(modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth(),
                                            onClick = {
                                                requestPermissionLauncher.launch(android.Manifest.permission.READ_SMS)
                                            }) {
                                            Text(
                                                text = "تایید دسترسی",
                                                fontFamily = ariaFaNumFontFamily,
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.Black,
                                            )
                                        }
                                    }
                                }
                            }

                        } else {
                            LaunchedEffect(key1 = "readSms&gotoMain") {
                                readSms(contentResolver, viewModel)
                            }
                        }



                        when (viewState.value) {
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


    private suspend fun readSms(contentResolver: ContentResolver, mainViewModel: MainViewModel) {
        val smsList = ArrayList<dev.estaki.data.entities.SmsRawModel>()
        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
                smsList.add(dev.estaki.data.entities.SmsRawModel(address, body))
            } while (cursor.moveToNext())

            mainViewModel.filterSmsData(smsList)

        }
        cursor?.close()
    }
}