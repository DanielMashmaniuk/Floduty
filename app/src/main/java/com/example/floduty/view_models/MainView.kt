package com.example.floduty.view_models

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.floduty.data.MainViewData
import com.example.floduty.ui.theme.Palette
import com.example.floduty.view_models.main_view_components.MainContent
import com.example.floduty.view_models.main_view_components.MiniCalendar
import com.example.floduty.view_models.main_view_components.NavButtons

@Composable
fun MainView(mainViewData: MainViewData) {
    val palette = Palette()
    val currentMonth = mainViewData.currentMonth.intValue
    val currentYear = mainViewData.currentYear.intValue
    val currentDay = mainViewData.currentDay.intValue

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.mainBG)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { switchOffScreens(mainViewData) }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center
        ){
            MainContent(currentYear,currentMonth,currentDay, palette,mainViewData)
        }
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            MiniCalendar(
                mainViewData = mainViewData,
                currentYear = currentYear,
                currentMonth = currentMonth,
                currentDay = currentDay,
                palette = palette,
            )
        }
        Box(
            modifier = Modifier.align(Alignment.Companion.TopStart)
        ) {
            NavButtons(palette,mainViewData)
        }
    }
}
fun switchOffScreens(mainViewData: MainViewData){
    if (mainViewData.isCalendarVisible.value){
        mainViewData.isCalendarVisible.value = false
    }
}




