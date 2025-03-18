package dev.estaki.kt_pure_utils

import java.text.DecimalFormat

fun String.removeSpecialChar(): String =
    if (this.contains("*"))
        this.filter { it != '*' }
    else if (this.contains("-"))
        this.filter { it != '-' }
    else if (this.contains("+"))
        this.filter { it != '+' }
    else if (this.contains(":"))
        this.filter { it != ':' }
    else
        this

fun String.removeFarsiChar(): String =
    if (this.isProbablyArabicOrPersian())
        this.removeArabicOrPersian().removeSpecialChar()
    else
        this.removeSpecialChar()

private fun String.removeArabicOrPersian(): String {
    val result = StringBuilder()
    for (codePoint in this.codePoints().toArray()) {
        if (codePoint !in 0x0600..0x06FF) { // Arabic and Persian Unicode range
            result.appendCodePoint(codePoint)
        }
    }
    return result.toString()
}

fun String.isProbablyArabicOrPersian(): Boolean {
    var i = 0
    while (i < this.length) {
        val c = this.codePointAt(i)
        if (c in 0x0600..0x06E0) return true
        i += Character.charCount(c)
    }
    return false
}

fun String.formatAmount(): String {
    // ابتدا تبدیل String به عدد
    return try {
        var myAmount = this
        if (myAmount.contains(",")){
            myAmount = myAmount.replace(",","")
        }
        val number = myAmount.toBigInteger() // فرض بر این است که عدد معتبر است
        val formatter = "%,d"// قالب سه رقم سه رقم جدا شده با ,
        formatter.format(number) // فرمت‌دهی عدد
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        "" // در صورت خطا
    }
}