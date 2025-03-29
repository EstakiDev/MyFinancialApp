package dev.estaki.ui_utils.utils

import android.content.Context
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
        }.collect {(currentIndex,currentScrollOffset) ->
            Timber.tag("LAZY_COlUMN").d("currentIndex -> $currentIndex")
            Timber.tag("LAZY_COlUMN").d("lastIndex -> $lastIndex")
            Timber.tag("LAZY_COlUMN").d("currentScrollOffset -> $currentScrollOffset")
            Timber.tag("LAZY_COlUMN").d("lastScroll -> $lastScroll")

            if (currentIndex != lastIndex || currentScrollOffset != lastScroll || (this@isScrollingUp.layoutInfo.totalItemsCount - currentIndex < 10)) {
                value = currentIndex < lastIndex || (currentIndex == lastIndex && currentScrollOffset < lastScroll)
//                Timber.tag("LAZY_COlUMN").d("isScrollingUp -> $value")
                lastIndex = currentIndex
                lastScroll = currentScrollOffset
            }
        }
    }
}