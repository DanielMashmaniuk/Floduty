package com.example.floduty.view_models.main_view_components.main_content_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.floduty.screens.MainViewData
import com.example.floduty.ui.theme.Palette
import com.example.floduty.ui.theme.palette

@Composable
fun DayInfo(currentYear: Int, currentMonth : Int, currentDay: Int, mainViewData: MainViewData) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(180.dp)
                .height(100.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp))
                    .background(palette.primaryColor)
            ) {

            }
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp))
                    .background(palette.primaryColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentDay.toString(),
                    color = palette.mainBG,
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Text(
            text = "${mainViewData.getMonthsNameByNumber(currentMonth)},$currentYear",
            color = palette.whiteColor,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(300.dp)
        ){
            TasksCountsBox(1, 1, mainViewData,palette )
            TasksCountsBox(2, 2, mainViewData,palette )
            TasksCountsBox(3, 3, mainViewData, palette )
            TasksCountsBox(4, 4, mainViewData, palette )
            TasksCountsBox(5, 5, mainViewData, palette )

        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(300.dp)
        ) {
            InfoBox("Busyness   ",1,palette,mainViewData)
            InfoBox("Intensity     ",3,palette,mainViewData)
            InfoBox("Importance",5,palette,mainViewData)
        }
    }
}

@Composable
fun InfoBox(description: String, level : Int, palette: Palette, mainViewData: MainViewData){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .width(200.dp)
            .height(30.dp)
    ) {
        Text(
            text = description,
            color = palette.whiteColor,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = ":",
            color = palette.whiteColor,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
        LevelBox(level,palette,mainViewData)
    }
}
@Composable
fun LevelBox(level: Int, palette: Palette, mainViewData: MainViewData){
    Box(
        modifier = Modifier
            .height(30.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(mainViewData.getLevelColor(level)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = mainViewData.getLevelName(level),
            color = palette.mainBG,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
@Composable
fun TasksCountsBox(tasks: Int, level: Int, mainViewData: MainViewData, palette: Palette){
    Box(
        modifier = Modifier
            .height(30.dp)
            .width(50.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(mainViewData.getLevelColor(level)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = tasks.toString(),
            color = palette.mainBG,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}