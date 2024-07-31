package dev.estaki.myfinancialapp

import android.Manifest
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.valentinilk.shimmer.shimmer
import dagger.hilt.android.AndroidEntryPoint
import dev.estaki.myfinancialapp.presentation.SplashScreen
import dev.estaki.myfinancialapp.ui.theme.ColorTextGrayOnDarkTheme
import dev.estaki.myfinancialapp.ui.theme.ColorTextGrayOnLiteTheme
import dev.estaki.myfinancialapp.ui.theme.FinancialTheme
import dev.estaki.myfinancialapp.ui.theme.coolakFaNumFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels<MainViewModel>()
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            readSms(contentResolver,viewModel)
        }


        setContent {
            FinancialTheme {
                Scaffold {
                    val alpha = remember {
                        Animatable(0f)
                    }
                    if (checkPermissions(this)) {
                        requestPermissionLauncher.launch(Manifest.permission.READ_SMS)
                    }
                    LaunchedEffect(key1 = true) {
                        alpha.animateTo(1f, animationSpec = tween(2000))
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        delay(4000)
                        startActivity(intent)
                        this@SplashActivity.finish()

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
                                modifier = Modifier.alpha(alpha.value).shimmer()

                            )
                            Text(
                                text = "به اپلیکیشن مدیریت خودکار دخل و خرج با استفاده از پیامک های بانکی خوش اومدی...",
                                modifier = Modifier.padding(12.dp).alpha(alpha.value).shimmer(),
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

                }

            }
        }
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