package dev.estaki.myFinancialApp.presentation

import dev.estaki.myFinancialApp.Utilities
import java.util.Date

fun main() {
    val date = Date(1692890168892)
    val cal = Utilities.SolarCalendar(date)
    val year = cal.year
    val mo =cal.month
    val day = cal.date

//    val string = "05/16"
//    val arrayOfDate = string.split("/").toMutableList()
//    if (arrayOfDate.size == 2){
//        arrayOfDate.add(0, year.toString())
//    }
    println(year)
    println(mo)
    println(day)
    println(Utilities.currentShamsidate)
}

