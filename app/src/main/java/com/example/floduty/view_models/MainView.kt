package com.example.floduty.view_models

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.floduty.R
import com.example.floduty.data.MainViewModel
import com.example.floduty.data.db.TaskDao
import com.example.floduty.ui.theme.Palette
import com.example.floduty.view_models.main_view_components.MainContent
import com.example.floduty.view_models.main_view_components.MiniCalendar
import com.example.floduty.view_models.main_view_components.NavButtons
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MainView(mainViewModel: MainViewModel) {
    val palette = Palette()
    val currentMonth = mainViewModel.currentMonth.intValue
    val currentYear = mainViewModel.currentYear.intValue
    val currentDay = mainViewModel.currentDay.intValue
    val isCalendarVisible = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.mainBG)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center
        ){
            MainContent(currentYear,currentMonth,currentDay, palette,mainViewModel)
        }
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            MiniCalendar(
                mainViewModel = mainViewModel,
                currentYear = currentYear,
                currentMonth = currentMonth,
                currentDay = currentDay,
                palette = palette,
                isCalendarVisible
            )
        }
        Box(
            modifier = Modifier.align(Alignment.Companion.TopStart)
        ) {
            NavButtons(palette = palette, isCalendarVisible = isCalendarVisible)
        }
    }
}





