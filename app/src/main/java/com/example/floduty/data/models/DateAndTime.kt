package com.example.floduty.data.models

import com.example.floduty.data.MainViewModel
import java.time.Year
import java.util.Date

data class DateAndTime(
    var year : Int,
    var month: Int,
    var day: Int,
    var hour: Int = 0,
    var minutes: Int = 0
) {
    fun changeData(
        newYear: Int = year,
        newMonth: Int = month,
        newDay: Int = day,
        newHour: Int = hour,
        newMinutes: Int = minutes
        ): DateAndTime
    {
        return DateAndTime(newYear, newMonth, newDay, newHour, newMinutes)
    }

    fun getDateTxtFormat(mainViewModel: MainViewModel): String{
        return "${convertToFormatWithZero(day)} ${mainViewModel.getMonthsNameByNumber(month)} $year"
    }
    fun getPatternFormat(): String{
        println("$year $month $day $hour $minutes")
        return "${convertToFormatWithZero(day)}-${convertToFormatWithZero(month)}-$year ${convertToFormatWithZero(hour)}:${convertToFormatWithZero(minutes)}"
    }
    private fun convertToFormatWithZero(n: Int) : String{
        return if (n < 10){
            "0$n"
        }else{
            n.toString()
        }
    }
}