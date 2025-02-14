package dev.estaki.myFinancialApp.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.estaki.myFinancialApp.ui.theme.ariaFaNumFontFamily

@Composable
fun AmountTextField(
    modifier: Modifier = Modifier,
    amount: String,
    label: String = "مبلغ",
    unit: String,
    onValueChange: (newAmount: String) -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = amount,
            onValueChange = {
                onValueChange.invoke(it)
            },

            label = {
                Text(
                    label,
                    style = TextStyle(fontSize = 17.sp, fontFamily = ariaFaNumFontFamily),
                    fontWeight = FontWeight.Bold
                )
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = 17.sp,
                fontFamily = ariaFaNumFontFamily,
                fontWeight = FontWeight.Bold
            ),
        )
        Text(
            text = unit,
            modifier = Modifier.align(Alignment.CenterEnd).padding(horizontal = 12.dp),
            style = TextStyle(
                fontSize = 17.sp,
                fontFamily = ariaFaNumFontFamily,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview
@Composable
fun AmountTextFieldPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        AmountTextField(amount = "12,450", unit = "تومان") {

        }
    }

}