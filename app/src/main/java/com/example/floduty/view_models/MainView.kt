package com.example.floduty.view_models

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.floduty.screens.MainViewData
import com.example.floduty.ui.theme.Palette
import com.example.floduty.ui.theme.palette
import com.example.floduty.view_models.main_view_components.MainContent
import com.example.floduty.view_models.main_view_components.MiniCalendar
import com.example.floduty.view_models.main_view_components.NavButtons
import com.example.floduty.view_models.main_view_components.QuickMessageBox

@Composable
fun MainView(mainViewData: MainViewData) {
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
            MainContent(currentYear,currentMonth,currentDay,mainViewData)
        }
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            MiniCalendar(
                mainViewData = mainViewData,
                currentYear = currentYear,
                currentMonth = currentMonth,
                currentDay = currentDay,
            )
        }
        Box(
            modifier = Modifier.align(Alignment.Companion.TopStart)
        ) {
            NavButtons(mainViewData)
        }
        Box(Modifier
            .width(getScreenWidth().dp)
            .align(Alignment.TopCenter)
        ){
            QuickMessageBox(mainViewData)
        }
    }
}
fun switchOffScreens(mainViewData: MainViewData){
    if (mainViewData.isCalendarVisible.value){
        mainViewData.isCalendarVisible.value = false
    }
}
@Composable
fun getScreenWidth(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp
}



