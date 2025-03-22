package dev.estaki.ui_utils.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import dev.estaki.ui_utils.ui.theme.ariaFaNumFontFamily

@Composable
fun MyAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        AlertDialog(
            icon = {
                Icon(icon, contentDescription = "Alert")
            },
            title = {
                Text(
                    text = dialogTitle,
                    style = TextStyle(fontFamily = ariaFaNumFontFamily, fontWeight = FontWeight.Black)
                )
            },
            text = {
                Text(text = dialogText,style = TextStyle(fontFamily = ariaFaNumFontFamily,fontWeight = FontWeight.SemiBold))
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text("باشه",style = TextStyle(fontFamily = ariaFaNumFontFamily,fontWeight = FontWeight.SemiBold))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("انصراف",style = TextStyle(fontFamily = ariaFaNumFontFamily,fontWeight = FontWeight.SemiBold))
                }
            }
        )
    }

}