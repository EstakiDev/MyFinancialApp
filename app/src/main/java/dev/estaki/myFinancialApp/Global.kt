package dev.estaki.myFinancialApp

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun isPermissionsGranted(context:Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.READ_SMS
    ) == PackageManager.PERMISSION_GRANTED
}

fun String.removeSpecialChar():String =
    if (this.contains("*"))
        this.filter { it != '*' }
    else
        this

fun String.isProbablyArabicOrPersian(): Boolean {
    var i = 0
    while (i < this.length) {
        val c = this.codePointAt(i)
        if (c in 0x0600..0x06E0) return true
        i += Character.charCount(c)
    }
    return false
}

fun String.extractionTimeOfDate():String =
    if (this.length > 5){
        if (this.contains(" ")){
            this.split(" ").find { it.contains(":") }?:"-"
        }else if (this.contains("_")){
            this.split("_").find { it.contains(":") }?:"-"
        }else{
            this
        }
    } else
        this

fun String.extractionDateOfTime():String =
    if (this.length > 8){
        if (this.contains(" ")){
            this.split(" ").find { it.contains("/") }?:"-"
        }else if (this.contains("_")){
            this.split("_").find { it.contains("/") }?:"-"
        }else{
            this
        }
    } else
        this