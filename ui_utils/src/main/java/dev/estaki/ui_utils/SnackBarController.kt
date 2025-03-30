package dev.estaki.ui_utils

import dev.estaki.domain.error.MyCustomSnackBarType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

data class SnackBarEvent(
    val message: String,
    val action: SnackBarAction? = null,
    val type: MyCustomSnackBarType
)

data class SnackBarAction(
    val name: String,
    val action: () -> Unit
)

object SnackBarController {
    private val _events = MutableSharedFlow<SnackBarEvent>()
    val events = _events.asSharedFlow()

    suspend fun sendEvent(event: SnackBarEvent) {
        _events.emit(event)
    }
}