package dev.estaki.myFinancialApp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.estaki.myFinancialApp.ui.theme.FinancialTheme
import dev.estaki.myFinancialApp.ui.theme.ariaFaNumFontFamily


@Composable
fun AddDetailScreen() {
    FinancialTheme {
        Scaffold {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                var text by remember {
                    mutableStateOf("")
                }
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    TextField(
                        modifier = Modifier
                            .padding(12.dp)
                            .padding(it)
                            .fillMaxWidth()
                            .fillMaxHeight(0.4F),
                        textStyle = TextStyle(fontSize = 18.sp, fontFamily = ariaFaNumFontFamily, fontWeight = FontWeight.Medium),
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