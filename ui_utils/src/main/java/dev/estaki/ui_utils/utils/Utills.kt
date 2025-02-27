package dev.estaki.ui_utils.utils

import android.graphics.Color
import kotlin.random.Random

fun generateRandomColor() : Int{
    val random = Random.Default
    val color = Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256))
    return color
}