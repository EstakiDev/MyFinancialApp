package dev.estaki.myFinancialApp.presentation

import dev.estaki.myFinancialApp.Utilities
import java.util.Date
import kotlin.text.split

fun main() {
//    val date = Date(1692890168892)
//    val cal = Utilities.SolarCalendar(date)
//    val year = cal.year
//    val mo =cal.month
//    val day = cal.date
//
////    val string = "05/16"
////    val arrayOfDate = string.split("/").toMutableList()
////    if (arrayOfDate.size == 2){
////        arrayOfDate.add(0, year.toString())
////    }
//    println(year)
//    println(mo)
//    println(day)
//    println(Utilities.currentShamsidate)

    val body = "\"*بانک قرض الحسنه رسالت* \n" +
            "بانکداري اجتماعي متمرکز (بام) \n" +
            "واريز به: 10.5767872.1 \n" +
            "مبلغ: 200,000,000 ريال \n" +
            "02/07/01_09:26 \n" +
            "موجودي: 219,087,790 ريال\""

    val split = body.split("\n")

    print((split.find {
        it.contains("برداشت از:") || it.contains("حساب:") || it.contains(
            "واريز به"
        )
    }))
}

