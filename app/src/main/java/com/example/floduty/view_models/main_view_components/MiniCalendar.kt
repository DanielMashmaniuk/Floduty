package com.example.floduty.view_models.main_view_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.floduty.R
import com.example.floduty.screens.MainViewData
import com.example.floduty.ui.theme.Palette
import com.example.floduty.ui.theme.palette
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MiniCalendar(mainViewData: MainViewData, currentYear: Int, currentMonth: Int, currentDay: Int){

    AnimatedVisibility(
        visible = mainViewData.isCalendarVisible.value,
        enter = fadeIn() + expandVertically(),  // Ефект появи
        exit = fadeOut() + shrinkVertically()   // Ефект зникнення
    ) {
        val monthCalendar = remember { mutableIntStateOf(currentMonth) }
        val yearCalendar = remember { mutableIntStateOf(currentYear) }

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(palette.secondBG)
        ) {

            CustomCalendar(yearCalendar.intValue, monthCalendar.intValue, currentDay, mainViewData)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(50.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.today),
                            contentDescription = "Today",
                            tint = palette.primaryColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Previous Button
                        IconButton(
                            onClick = {
                                val dateCalendar = setPreviousMonthCalendar(monthCalendar.intValue,yearCalendar.intValue)
                                monthCalendar.intValue = dateCalendar.second
                                yearCalendar.intValue = dateCalendar.first
                            },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_prev),
                                contentDescription = "Previous Month",
                                tint = palette.whiteColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Month and Year Text
                        Text(
                            text = "${mainViewData.getMonthsNameByNumber(monthCalendar.intValue)}, ${yearCalendar.intValue}",
                            color = palette.primaryColor,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        // Next Button
                        IconButton(
                            onClick = {
                                val dateCalendar = setNextMonthCalendar(monthCalendar.intValue,yearCalendar.intValue)
                                monthCalendar.intValue = dateCalendar.second
                                yearCalendar.intValue = dateCalendar.first
                            },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_next),
                                contentDescription = "Next Month",
                                tint = palette.whiteColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    IconButton(
                        onClick = { mainViewData.setPreviousMonth() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.maximize_square_minimalistic),
                            contentDescription = "Today",
                            tint = palette.primaryColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun CustomCalendar(year: Int, month: Int, currentDay: Int, mainViewData: MainViewData) {
    val calendarData = generateCalendarData(year,month)

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Відображення назв днів тижня
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                WeekBox(day)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Відображення днів місяця
        calendarData.forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEach { day ->
                    if (mainViewData.isCurrentDay(year,day!!.monthValue, day.dayOfMonth)) {
                        CurrentDayBox(day.dayOfMonth ) // Для поточного місяця
                    } else if (day != null) {
                        if (day.monthValue != month) {
                            InactiveDayBox(day.dayOfMonth) // Для минулого або наступного місяця
                        } else {
                            DayBox(day.dayOfMonth.toString()) // Для поточного місяця
                        }
                    } else {
                        InactiveDayBox(-1) // Для порожніх клітинок
                    }
                }
            }
        }
    }
}
fun setPreviousMonthCalendar(month: Int,year: Int): Pair<Int,Int> {
    if (month == 1) {
        val newMonth = 12
        val newYear = year - 1
        return Pair(newYear,newMonth)
    } else {
        val newMonth = month - 1
        return Pair(year,newMonth)
    }
}
fun setNextMonthCalendar(month: Int,year: Int): Pair<Int,Int> {
    if (month == 12) {
        val newMonth = 1
        val newYear = year + 1
        return Pair(newYear,newMonth)
    } else {
        val newMonth = month + 1
        return Pair(year,newMonth)
    }
}
fun getDaysOfMonth(year: Int,month: Int): Int{
    return YearMonth.of(year, month).lengthOfMonth()
}
fun getFirstDayOfMonth(year: Int,month: Int): Int{
    return LocalDate.of(year, month, 1).dayOfWeek.value
}
fun generateCalendarData(year: Int, month: Int): List<List<LocalDate?>> {
    val daysInMonth = getDaysOfMonth(year, month)
    val firstDayOfMonth = getFirstDayOfMonth(year, month)

    // Останній день поточного місяця
    val lastDayOfMonth = LocalDate.of(year, month, daysInMonth)

    val calendar = mutableListOf<List<LocalDate?>>()
    var week = mutableListOf<LocalDate?>()

    // Додаємо пусті клітинки перед першим днем місяця, заповнюємо днями з попереднього місяця
    val previousMonth = YearMonth.of(year, month).minusMonths(1) // Отримуємо попередній місяць
    val previousMonthDays = previousMonth.lengthOfMonth() // Кількість днів в попередньому місяці

    // Додаємо пусті клітинки перед першим днем місяця, заповнюємо днями з попереднього місяця
    repeat((firstDayOfMonth - 1) % 7) { index ->
        week.add(LocalDate.of(year, previousMonth.monthValue, previousMonthDays - (firstDayOfMonth - 2) + index))
    }

    // Додаємо дні поточного місяця
    for (day in 1..daysInMonth) {
        week.add(LocalDate.of(year, month, day))
        if (week.size == 7) {
            calendar.add(week)
            week = mutableListOf()
        }
    }

    // Заповнюємо порожні клітинки в кінці місяця днями з наступного місяця
    val nextMonth = YearMonth.of(year, month).plusMonths(1) // Отримуємо наступний місяць
    repeat(7 - week.size) { index ->
        week.add(LocalDate.of(year, nextMonth.monthValue, index + 1))
    }

    // Якщо є залишкові дні, додаємо їх в календар
    if (week.isNotEmpty()) calendar.add(week)

    return calendar
}