package com.example.floduty.data

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.floduty.ui.theme.Palette
import java.time.LocalDate
import java.time.Month
import java.time.MonthDay
import java.time.Year

class MainViewModel : ViewModel() {
    val palette = Palette()
    var currentMonth = mutableIntStateOf(getCurrentMonth())
    var currentYear = mutableIntStateOf(getCurrentYear())
    var currentDay = mutableIntStateOf(getCurrentDay())
    private val nameMonthByNumber = mapOf(
        1 to "January",
        2 to "February",
        3 to "March",
        4 to "April",
        5 to "May",
        6 to "June",
        7 to "July",
        8 to "August",
        9 to "September",
        10 to "October",
        11 to "November",
        12 to "December"
    )
    private val nameLevelByNumber = mapOf(
        1 to "Simple",
        2 to "Easy",
        3 to "Medium",
        4 to "Hard",
        5 to "Extreme"
    )
    private val colorLevelByNumber = mapOf(
        1 to palette.simpleLevelColor,
        2 to palette.easyColor,
        3 to palette.mediumColor,
        4 to palette.hardColor,
        5 to palette.extremeColor
    )
    fun getLevelName(level: Int): String =
        nameLevelByNumber[level] ?: "Unknown"

    fun getLevelColor(level: Int): Color =
        colorLevelByNumber[level] ?: Color(0xFF4D6651)

    fun getMonthsNameByNumber(number: Int): String {
        return nameMonthByNumber[number] ?: "Invalid Month"
    }

    fun setNextMonth() {
        var month = currentMonth.intValue
        var year = currentYear.intValue

        if (month == 12) {
            month = 1
            year += 1
        } else {
            month += 1
        }

        currentMonth.intValue = month
        currentYear.intValue = year
    }

    fun setPreviousMonth() {
        var month = currentMonth.intValue
        var year = currentYear.intValue

        if (month == 1) {
            month = 12
            year -= 1
        } else {
            month -= 1
        }

        currentMonth.intValue = month
        currentYear.intValue = year
    }
    fun setCurrentDay(){
        currentMonth.intValue = getCurrentMonth()
        currentYear.intValue = getCurrentYear()
    }
    fun isCurrentDay(year: Int,month: Int,day: Int): Boolean{
        val currentMonth = getCurrentMonth()
        val currentYear = getCurrentYear()
        val currentDay = getCurrentDay()
        return year == currentYear && month == currentMonth && day == currentDay
    }
}

fun getCurrentMonth(): Int {
    val currentDate = LocalDate.now()
    return currentDate.monthValue
}

fun getCurrentYear(): Int {
    val currentDate = LocalDate.now()
    return currentDate.year
}
fun getCurrentDay(): Int{
    val currentDate = LocalDate.now()
    return currentDate.dayOfMonth
}