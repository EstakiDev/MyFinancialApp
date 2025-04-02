package dev.estaki.kt_pure_utils

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

@Throws(NumberFormatException::class)
fun String.formatCardNumber(): String  {
    var input = this.replace(" ","")
    if (input.any { char -> char.isDigit().not() })
        throw NumberFormatException("شما نمیتوانید بجز اعداد وارد کنید")
    if (input.length> 16){
        throw NumberFormatException("شماره کارت نمیتواند بیشتر از 16 رقم باشد.")
    }
    else{
        if (input.length>= 4)
            input = input.chunked(4).joinToString(separator = " ")
    }

    return input
}

fun resetErr(): Pair<Boolean, String>{
    return Pair(false,"")
}

const val arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9"
fun String.arabicToDecimal(): String {
    val chars = CharArray(this.length)
    for (i in 0..<this.length) {
        var ch = this[i]
        if (ch.code >= 0x0660 && ch.code <= 0x0669) ch -= (0x0660 - '0'.code).toChar().code
        else if (ch.code >= 0x06f0 && ch.code <= 0x06F9) ch -= (0x06f0 - '0'.code).toChar().code
        chars[i] = ch
    }
    return String(chars)
}
