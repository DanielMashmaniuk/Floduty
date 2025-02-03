package com.example.floduty.data.models

import com.example.floduty.screens.MainViewData

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

    fun getDateTxtFormat(mainViewData: MainViewData): String{
        return "${"%02d".format(day)} ${mainViewData.getMonthsNameByNumber(month)} $year"
    }
    fun getPatternFormat(): String {
        return "%02d-%02d-%d %02d:%02d".format(day, month, year, hour, minutes)
    }
    fun getDateDatabaseFormat(): String {
        return "$year-${"%02d".format((month))}-${"%02d".format((day))}"
    }

}