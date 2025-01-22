package com.example.floduty.view_models.main_view_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.floduty.data.MainViewData
import com.example.floduty.ui.theme.Palette
import com.example.floduty.view_models.main_view_components.main_content_components.DayInfo
import com.example.floduty.view_models.main_view_components.main_content_components.ScheduleNav
import com.example.floduty.view_models.main_view_components.main_content_components.ScheduleTable
import com.example.floduty.view_models.main_view_components.main_content_components.nav_functions.CreateNewTaskBox

@Composable
fun MainContent(currentYear: Int, currentMonth : Int, currentDay: Int, palette: Palette, mainViewData: MainViewData) {

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Додаємо вертикальний скрол
    ) {
        Box(
            modifier = Modifier
                .size(400.dp),
            contentAlignment = Alignment.Center
        ) {
            DayInfo(
                currentYear = currentYear,
                currentMonth = currentMonth,
                currentDay = currentDay,
                palette = palette,
                mainViewData = mainViewData
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            ScheduleNav(
                currentYear = currentYear,
                currentMonth = currentMonth,
                currentDay = currentDay,
                palette = palette,
                mainViewData = mainViewData,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1000.dp),
            contentAlignment = Alignment.Center
        ) {
            ScheduleTable(
                palette = palette,
                mainViewData
            ){ hour, minute ->
                println("Selected time: $hour:$minute")
            }
        }
    }
    CreateNewTaskBox(palette,mainViewData)

}