package dev.estaki.ui_utils.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.estaki.ui_utils.ui.theme.DarkYellow
import dev.estaki.ui_utils.ui.theme.ariaFaNumFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionBottomSheet(modifier: Modifier = Modifier, modalBottomSheetState: SheetState, onBtnGetPermissionClicked:()-> Unit, onDismissRequest:()-> Unit) {

    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest.invoke()
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
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "سلام رفیق\n اپلیکیشن برای این که بتونه پیامک تراکنش های بانکی تو رو بهت نشون بده نیاز داره که تو این دسترسی رو تایید کنی.",
                fontFamily = ariaFaNumFontFamily,
                fontSize = 15.sp,
                lineHeight = 25.sp,
                fontWeight = FontWeight.Bold,
            )

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                onClick = { onBtnGetPermissionClicked.invoke() },
                border = BorderStroke(1.dp, DarkYellow),
                shape = RoundedCornerShape(20), // = 20% percent
                // or shape = CircleShape
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = DarkYellow
                )
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 6.dp),
                    text = "تایید دسترسی",
                    fontFamily = ariaFaNumFontFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    color = DarkYellow
                )
            }
        }
    }
}