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
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun DayInfo(vData: MainViewData) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View) // Ініціалізуємо ефект

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
                    text = vData.currentDay.intValue.toString(),
                    color = palette.mainBG,
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Text(
            text = "${vData.getMonthsNameByNumber(vData.currentMonth.intValue)},${vData.currentYear.intValue}",
            color = palette.whiteColor,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,

        ){
            val simpleTasks  = vData.tasks.value.filter { it.weight == 1 }
            val easyTasks  = vData.tasks.value.filter { it.weight == 2 }
            val mediumTasks  = vData.tasks.value.filter { it.weight == 3 }
            val hardTasks  = vData.tasks.value.filter { it.weight == 4 }
            val extremeTasks  = vData.tasks.value.filter { it.weight == 5 }
            if (simpleTasks.isNotEmpty()){
                TasksCountsBox(simpleTasks.size, 1, vData )
            }
            if (easyTasks.isNotEmpty()){
                TasksCountsBox(easyTasks.size, 2, vData )
            }
            if (mediumTasks.isNotEmpty()){
                TasksCountsBox(mediumTasks.size, 3, vData )
            }
            if (hardTasks.isNotEmpty()){
                TasksCountsBox(hardTasks.size, 4, vData )
            }
            if (extremeTasks.isNotEmpty()){
                TasksCountsBox(extremeTasks.size, 5, vData )
            }
            if (simpleTasks.isEmpty() && easyTasks.isEmpty() && mediumTasks.isEmpty() && hardTasks.isEmpty() && extremeTasks.isEmpty()){
                TasksCountsBox(0,0,vData)
            }

        }
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .width(300.dp)
        ) {
            InfoBox("Busyness   ",vData.busynessLevel.intValue,vData)
            InfoBox("Intensity     ",3,vData)
            InfoBox("Importance",5,vData)
        }
    }
}
@Composable
fun InfoBox(description: String, level : Int, mainViewData: MainViewData){
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
fun TasksCountsBox(tasks: Int, level: Int, mainViewData: MainViewData){
    val zero = level == 0
    Box(
        modifier = Modifier
            .height(30.dp)
            .width(50.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (zero) palette.lightGaryBG else mainViewData.getLevelColor(level)),
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