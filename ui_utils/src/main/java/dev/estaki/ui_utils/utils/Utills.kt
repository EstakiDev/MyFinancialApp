package dev.estaki.ui_utils.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.DisplayMetrics
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import timber.log.Timber

fun Context.pxToDp(px: Float): Float {
    val displayMetrics: DisplayMetrics = this.resources.displayMetrics
    return px / displayMetrics.density
}

@Composable
fun LazyListState.isScrollingUp(): State<Boolean> {
    return produceState(initialValue = true) {
        var lastIndex = 0
        var lastScroll = Int.MAX_VALUE
        snapshotFlow {
            firstVisibleItemIndex to firstVisibleItemScrollOffset
        }.collect { (currentIndex, currentScrollOffset) ->
            if (currentIndex != lastIndex || currentScrollOffset != lastScroll || (this@isScrollingUp.layoutInfo.totalItemsCount - currentIndex < 10)) {
                value =
                    currentIndex < lastIndex || (currentIndex == lastIndex && currentScrollOffset < lastScroll)
                lastIndex = currentIndex
                lastScroll = currentScrollOffset
            }
        }
    }
}

fun Activity.showAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName,null)
        ).also(::startActivity)
}