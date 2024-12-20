package com.example.floduty.view_models.main_view_components.main_content_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.floduty.data.MainViewModel
import com.example.floduty.ui.theme.Palette

@Composable
fun DayInfo(currentYear: Int, currentMonth : Int, currentDay: Int, palette: Palette, mainViewModel: MainViewModel) {
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
            text = "${mainViewModel.getMonthsNameByNumber(currentMonth)},$currentYear",
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
            TasksCountsBox(1, 1, mainViewModel,palette )
            TasksCountsBox(2, 2, mainViewModel,palette )
            TasksCountsBox(3, 3, mainViewModel, palette )
            TasksCountsBox(4, 4, mainViewModel, palette )
            TasksCountsBox(5, 5, mainViewModel, palette )

        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(300.dp)
        ) {
            InfoBox("Busyness   ",1,palette,mainViewModel)
            InfoBox("Intensity     ",3,palette,mainViewModel)
            InfoBox("Importance",5,palette,mainViewModel)
        }
    }
}

@Composable
fun InfoBox(description: String,level : Int,palette: Palette,mainViewModel: MainViewModel){
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
        Box(
            modifier = Modifier
                .height(30.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(mainViewModel.getLevelColor(level)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = mainViewModel.getLevelName(level),
                color = palette.mainBG,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
@Composable
fun TasksCountsBox(tasks: Int,level: Int,mainViewModel: MainViewModel,palette: Palette){
    Box(
        modifier = Modifier
            .height(30.dp)
            .width(50.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(mainViewModel.getLevelColor(level)),
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