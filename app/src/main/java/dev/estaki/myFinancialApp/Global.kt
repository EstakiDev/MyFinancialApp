package dev.estaki.myFinancialApp

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import java.util.Date
import java.util.Locale

fun isPermissionsGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.READ_SMS
    ) == PackageManager.PERMISSION_GRANTED
}

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

fun String.extractionTimeOfDate(): String =
    if (this.length > 5) {
        if (this.contains(" ")) {
            this.split(" ").find { it.contains(":") } ?: "-"
        } else if (this.contains("_")) {
            this.split("_").find { it.contains(":") } ?: "-"
        } else {
            this
        }
    } else
        this

fun String.extractionDateOfTime(): String =
    if (this.length > 8) {
        if (this.contains(" ")) {
            this.split(" ").find { it.contains("/") } ?: "-"
        } else if (this.contains("_")) {
            this.split("_").find { it.contains("/") } ?: "-"
        } else {
            this
        }
    } else
        this

fun String.addYearToDate() {
    val gc = Utilities.currentShamsidate
    val year = Utilities.SolarCalendar().year
    val arrayOfDate = this.split("/").toMutableList()
    if (arrayOfDate.size == 2) {
        arrayOfDate.add(0, year.toString())
    }
    println(arrayOfDate.joinToString(separator = "/"))

}

fun String.convertToTime(): String {
    val date = Date(this.toLong())
    val cal = Utilities.SolarCalendar(date)
    val year = cal.year
    val mo = cal.month
    val day = cal.date
    return "$year/$mo/$day"
}


object Utilities {
    val currentShamsidate: String
        get() {
            val loc: Locale = Locale("en_US")
            val util: Utilities = Utilities
            val sc = SolarCalendar()
            return sc.year.toString() + "/" + java.lang.String.format(
                loc, "%02d",
                sc.month
            ) + "/" + java.lang.String.format(loc, "%02d", sc.date)
        }

    class SolarCalendar {
        var strWeekDay: String = ""
        var strMonth: String = ""

        var date: Int = 0
        var month: Int = 0
        var year: Int = 0

        constructor() {
            val MiladiDate: Date = Date()
            calcSolarCalendar(MiladiDate)
        }

        constructor(MiladiDate: Date) {
            calcSolarCalendar(MiladiDate)
        }

        private fun calcSolarCalendar(MiladiDate: Date) {
            val ld: Int

            val miladiYear: Int = MiladiDate.getYear() + 1900
            val miladiMonth: Int = MiladiDate.getMonth() + 1
            val miladiDate: Int = MiladiDate.getDate()
            val WeekDay: Int = MiladiDate.getDay()

            val buf1 = IntArray(12)
            val buf2 = IntArray(12)

            buf1[0] = 0
            buf1[1] = 31
            buf1[2] = 59
            buf1[3] = 90
            buf1[4] = 120
            buf1[5] = 151
            buf1[6] = 181
            buf1[7] = 212
            buf1[8] = 243
            buf1[9] = 273
            buf1[10] = 304
            buf1[11] = 334

            buf2[0] = 0
            buf2[1] = 31
            buf2[2] = 60
            buf2[3] = 91
            buf2[4] = 121
            buf2[5] = 152
            buf2[6] = 182
            buf2[7] = 213
            buf2[8] = 244
            buf2[9] = 274
            buf2[10] = 305
            buf2[11] = 335

            if ((miladiYear % 4) != 0) {
                date = buf1[miladiMonth - 1] + miladiDate

                if (date > 79) {
                    date = date - 79
                    if (date <= 186) {
                        when (date % 31) {
                            0 -> {
                                month = date / 31
                                date = 31
                            }

                            else -> {
                                month = (date / 31) + 1
                                date = (date % 31)
                            }
                        }
                        year = miladiYear - 621
                    } else {
                        date = date - 186

                        when (date % 30) {
                            0 -> {
                                month = (date / 30) + 6
                                date = 30
                            }

                            else -> {
                                month = (date / 30) + 7
                                date = (date % 30)
                            }
                        }
                        year = miladiYear - 621
                    }
                } else {
                    ld = if ((miladiYear > 1996) && (miladiYear % 4) == 1) {
                        11
                    } else {
                        10
                    }
                    date = date + ld

                    when (date % 30) {
                        0 -> {
                            month = (date / 30) + 9
                            date = 30
                        }

                        else -> {
                            month = (date / 30) + 10
                            date = (date % 30)
                        }
                    }
                    year = miladiYear - 622
                }
            } else {
                date = buf2[miladiMonth - 1] + miladiDate

                ld = if (miladiYear >= 1996) {
                    79
                } else {
                    80
                }
                if (date > ld) {
                    date = date - ld

                    if (date <= 186) {
                        when (date % 31) {
                            0 -> {
                                month = (date / 31)
                                date = 31
                            }

                            else -> {
                                month = (date / 31) + 1
                                date = (date % 31)
                            }
                        }
                        year = miladiYear - 621
                    } else {
                        date = date - 186

                        when (date % 30) {
                            0 -> {
                                month = (date / 30) + 6
                                date = 30
                            }

                            else -> {
                                month = (date / 30) + 7
                                date = (date % 30)
                            }
                        }
                        year = miladiYear - 621
                    }
                } else {
                    date = date + 10

                    when (date % 30) {
                        0 -> {
                            month = (date / 30) + 9
                            date = 30
                        }

                        else -> {
                            month = (date / 30) + 10
                            date = (date % 30)
                        }
                    }
                    year = miladiYear - 622
                }
            }

            when (month) {
                1 -> strMonth = "فروردين"
                2 -> strMonth = "ارديبهشت"
                3 -> strMonth = "خرداد"
                4 -> strMonth = "تير"
                5 -> strMonth = "مرداد"
                6 -> strMonth = "شهريور"
                7 -> strMonth = "مهر"
                8 -> strMonth = "آبان"
                9 -> strMonth = "آذر"
                10 -> strMonth = "دي"
                11 -> strMonth = "بهمن"
                12 -> strMonth = "اسفند"
            }
            when (WeekDay) {
                0 -> strWeekDay = "يکشنبه"
                1 -> strWeekDay = "دوشنبه"
                2 -> strWeekDay = "سه شنبه"
                3 -> strWeekDay = "چهارشنبه"
                4 -> strWeekDay = "پنج شنبه"
                5 -> strWeekDay = "جمعه"
                6 -> strWeekDay = "شنبه"
            }
        }
    }
}
