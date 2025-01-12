package com.example.floduty.data.models

import com.example.floduty.data.MainViewModel

data class DateAndTime(
    var year : Int,
    var month: Int,
    var day: Int,
    var hour: Int = 0,
    var minutes: Int = 0
) {
    fun getDateTxtFormat(mainViewModel: MainViewModel): String{
        return "${convertToFormatWithZero(day)} ${mainViewModel.getMonthsNameByNumber(month)} $year"
    }
    fun getPatternFormat(): String{
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