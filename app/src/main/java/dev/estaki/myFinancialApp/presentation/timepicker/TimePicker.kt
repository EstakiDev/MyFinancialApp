package dev.estaki.myFinancialApp.presentation.timepicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.room.Room
import dev.estaki.myFinancialApp.ui.theme.FinancialTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePicker(
    onConfirm: (state: TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    FinancialTheme() {
        Dialog(onDismissRequest = {
            onDismiss.invoke()
        }) {
            Card(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Spacer(modifier = Modifier.size(24.dp))
                    TimePicker(
                        modifier = Modifier.fillMaxWidth(),
                        state = timePickerState,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("انصراف")
                        }
                        TextButton(onClick = { onConfirm.invoke(timePickerState) }) {
                            Text("تایید")
                        }
                    }

                }
            }

        }
    }



}