package dev.estaki.myFinancialApp.presentation.detailScreen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import dev.estaki.myFinancialApp.ui.theme.ColorTextGrayOnDarkTheme
import dev.estaki.myFinancialApp.ui.theme.ColorTextGrayOnLiteTheme
import dev.estaki.myFinancialApp.ui.theme.FinancialTheme
import dev.estaki.myFinancialApp.ui.theme.ariaFaNumFontFamily


@Composable
fun AddDetailScreen(detailScreenViewModel: DetailScreenViewModel = hiltViewModel()) {
    FinancialTheme {
        Scaffold {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                var text by remember {
                    mutableStateOf("")
                }
                val scrollState = rememberScrollState()

                val categoryList by detailScreenViewModel.categoryListLiveData.observeAsState()
                val loadingState by detailScreenViewModel.isLoading.collectAsState()
                Box(
                    Modifier.fillMaxSize(),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5F)
                            .verticalScroll(scrollState)
                    ) {

                        TextField(
                            modifier = Modifier
                                .padding(12.dp)
                                .padding(it)
                                .fillMaxSize()
                                .fillMaxHeight(),
                            textStyle = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = ariaFaNumFontFamily,
                                fontWeight = FontWeight.Medium
                            ),
                            placeholder = {
                                Text(
                                    fontFamily = ariaFaNumFontFamily,
                                    fontWeight = FontWeight.Black,
                                    text = "توضیحات تراکنشت رو اینجا وارد کن...",
                                    fontSize = 18.sp
                                )
                            },
                            value = text,
                            onValueChange = {
                                text = it
                            })
                        if (!categoryList.isNullOrEmpty()) {
                            LazyRow(
                                Modifier
                                    .padding(12.dp)
                                    .fillMaxHeight(0.6F)
                                    .fillMaxWidth()
                            ) {

                                items(categoryList?.size!!, itemContent = { ind ->
                                    Button(
                                        modifier = Modifier
                                            .padding(4.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        onClick = { /*TODO*/ }) {

                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Row {
                                                Checkbox(checked = , onCheckedChange = )
                                                Text(
                                                    fontFamily = ariaFaNumFontFamily,
                                                    fontWeight = FontWeight.SemiBold,
                                                    text = categoryList!![ind].title,
                                                    textAlign = TextAlign.Center,
                                                    style = TextStyle(fontSize = 13.sp),
                                                )
                                            }

                                        }
                                    }

                                })
                            }
                        }

                    }
                    BallPulseProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(0.dp, 32.dp)
                            .alpha(if (loadingState) 1f else 0f),
                        color = if (isSystemInDarkTheme()) ColorTextGrayOnDarkTheme else ColorTextGrayOnLiteTheme,
                        animationDuration = 800,
                        animationDelay = 200,
                        startDelay = 0,
                        ballCount = 3,
                        maxBallDiameter = 17.dp

                    )

                }

            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddDetailScreenPreview() {
    AddDetailScreen()
}