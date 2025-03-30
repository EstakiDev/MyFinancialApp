package dev.estaki.ui_utils.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.estaki.kt_pure_utils.formatAmount
import dev.estaki.ui_utils.ui.theme.ariaFaNumFontFamily

@Composable
fun AmountTextField(
    modifier: Modifier = Modifier,
    amount: TextFieldValue,
    label: String = "مبلغ",
    unit: String,
    isError: Boolean = false,
    supportingText : @Composable (()->Unit)? = null,
    onValueChange: (newAmount: TextFieldValue) -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(modifier = modifier) {
            TextField(
                value = amount,
                onValueChange = {
                    val newString = it.text.formatAmount()
                    val newValue = it.copy(
                        text = newString,
                        selection = TextRange(index = newString.length)
                    )
                    onValueChange.invoke(newValue)
                },
                isError = isError,
                supportingText = supportingText,
                label = {
                    Text(
                        label,
                        style = TextStyle(fontSize = 17.sp, fontFamily = ariaFaNumFontFamily),
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = ariaFaNumFontFamily,
                    fontWeight = FontWeight.Bold
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    Text(
                        text = unit,
                        modifier = Modifier
                            .padding(horizontal = 12.dp),
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontFamily = ariaFaNumFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun AmountTextFieldPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        AmountTextField(amount = TextFieldValue(text = "12,450"), unit = "تومان") {

        }
    }

}