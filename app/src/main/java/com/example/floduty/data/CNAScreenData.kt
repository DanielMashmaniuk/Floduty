package com.example.floduty.data

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.floduty.ui.theme.Palette

class CNAScreenData(mViewData: MainViewData,p: Palette) {
    val palette = p
    val mainViewData = mViewData
    //CNT
    val startDate = mutableStateOf(mainViewData.getNextDate(nd = 1))
    val endDate = mutableStateOf(mainViewData.getNextDate(nd = 2))
    val nameActivity =  mutableStateOf("Task")
    val notes =  mutableStateListOf<String>()
    val titleActivity =  mutableStateOf("")
    val descriptionActivity =  mutableStateOf("")
    val currentLevel = mutableIntStateOf(1)
    val textDifference = derivedStateOf {
            mainViewData.calculateTimeDifference(startDate.value, endDate.value)
        }

    val color = derivedStateOf {
        if (nameActivity.value == "Task") {
            palette.primaryColor
        } else {
            palette.eventColor
        }
    }


    val isNotePanelVisible = mutableStateOf(false)
    val isTimeSwitcherWindowVisible =  mutableStateOf(Pair("none", startDate))

    fun checkIsComplete(): String {
        val currentDate = listOf(
            mainViewData.currentYear.intValue,
            mainViewData.currentMonth.intValue,
            mainViewData.currentDay.intValue,
            mainViewData.currentHour.intValue,
            mainViewData.currentMinute.intValue
        )

        val endDateList = listOf(
            endDate.value.year,
            endDate.value.month,
            endDate.value.day,
            endDate.value.hour,
            endDate.value.minutes
        )

        val startDateList = listOf(
            startDate.value.year,
            startDate.value.month,
            startDate.value.day,
            startDate.value.hour,
            startDate.value.minutes
        )

        // Послідовне порівняння елементів списку для коректного визначення статусу
        return when {
            endDateList.zip(currentDate).any { (end, current) -> end < current } -> "Done"
            startDateList.zip(currentDate).any { (start, current) -> start < current } -> "Doing"
            else -> "Not"
        }
    }
}