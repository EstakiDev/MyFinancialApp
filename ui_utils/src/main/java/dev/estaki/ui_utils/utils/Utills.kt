package dev.estaki.ui_utils.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.DisplayMetrics
import android.util.TypedValue
import kotlin.math.roundToInt
import kotlin.random.Random

fun Context.pxToDp(px: Float): Float {
    val displayMetrics: DisplayMetrics = this.resources.displayMetrics
    return px / displayMetrics.density
}
