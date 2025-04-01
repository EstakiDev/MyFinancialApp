package dev.estaki.ui_utils.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.estaki.domain.error.MyCustomSnackBarType
import dev.estaki.ui_utils.R
import dev.estaki.ui_utils.SnackBarEvent
import dev.estaki.ui_utils.ui.theme.ariaFaNumFontFamily
import timber.log.Timber


@Composable
fun MyCustomSnackBar(modifier: Modifier = Modifier,snackBarEvent: SnackBarEvent,type :MyCustomSnackBarType,onAction: () -> Unit ) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        Card(
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                SnackBarIconLottie(type = type)
                Text(
                    modifier = modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(fraction = 0.7F),
                    text = snackBarEvent.message,
                    fontFamily = ariaFaNumFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp, lineHeight = 20.sp
                )
                TextButton(modifier = modifier.width(150.dp), onClick = {
                    onAction.invoke()
                    Timber.i("TextButton clicked")
                }) {
                    Text(snackBarEvent.action?.name ?: "باشه", fontFamily = ariaFaNumFontFamily)
                }
            }
        }
    }
}

@Composable
fun SnackBarIconLottie(modifier: Modifier = Modifier,type: MyCustomSnackBarType) {

    val res = when(type){
        MyCustomSnackBarType.SUCCESS -> R.raw.sucess_action
        MyCustomSnackBarType.ERROR ->   R.raw.failed_action
    }
    val splashScreenLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            res
        )
    )

    val splashScreenProgress by animateLottieCompositionAsState(
        composition = splashScreenLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1.5F,
    )
    LottieAnimation(
        composition = splashScreenLottieComposition,
        progress = splashScreenProgress,
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = if (res == R.raw.sucess_action) 0.dp else 22.dp)
            .size(if (res == R.raw.sucess_action) 48.dp else 22.dp),
        contentScale = ContentScale.Inside
    )


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyCustomSnackBarPre(modifier: Modifier = Modifier) {
    MyCustomSnackBar(
        type = MyCustomSnackBarType.SUCCESS,
        modifier = modifier,
        snackBarEvent = SnackBarEvent("test", type = MyCustomSnackBarType.SUCCESS)
    ){}
}