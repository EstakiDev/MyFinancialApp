package dev.estaki.myFinancialApp.presentation.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePicker(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )


        Dialog(onDismissRequest = {

        }) {
            Card(modifier = Modifier.padding(12.dp)) {
                Column {
                    TimePicker(
                        state = timePickerState,
                    )
                    Button(onClick = onDismiss) {
                        Text("Dismiss picker")
                    }
                    Button(onClick = onConfirm) {
                        Text("Confirm selection")
                    }

                }
            }

        }

}