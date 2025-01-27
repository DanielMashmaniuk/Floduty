package com.example.floduty.view_models.main_view_components.main_content_components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.floduty.data.models.Task
import com.example.floduty.screens.MainViewData
import com.example.floduty.ui.theme.Palette
import com.example.floduty.ui.theme.palette

@Composable
fun ScheduleTable(
    mainViewData: MainViewData,
    onTimeSelected: (hour: Int, minute: Int) -> Unit
) {
    val hours = (0..23).map { it.toString().padStart(2, '0') }
    val minutes = (0..60 step 5).toList()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.mainBG)
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(15.dp)) // Для годин зліва
            hours.forEach { hour ->
                if (hour.toInt() == mainViewData.currentHour.intValue) {
                    Text(
                        text = hour,
                        color = palette.orangeColor,
                        modifier = Modifier.width(28.dp).height(15.dp),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    )
                }else {
                    Text(
                        text = hour,
                        color = palette.primaryColor,
                        modifier = Modifier.width(28.dp).height(15.dp),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState())

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                minutes.forEach { minute ->
                    val mText = if (minute < 10) "0${minute}" else minute.toString()
                    if(checkActiveTime(mainViewData) == minute){
                        Text(
                            modifier = Modifier.width(20.dp),
                            text = mText,
                            color = palette.orangeColor,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        )
                    }else {
                        Text(
                            modifier = Modifier.width(20.dp),
                            text = mText,
                            color = palette.primaryColor,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                hours.forEach { hour ->
                    Row {
                        SetTimeBoxes(hour.toInt(),mainViewData)
                    }
                }
            }
        }
    }
}
@Composable
fun SetTimeBoxes(hour: Int, mainViewData: MainViewData){
    var min = 0
    while (min < 60){
        val time = hour * 60 + min
        val res = checkTaskAtTime(time,mainViewData.tasks)
        if (res.first){
            TaskBox(res.second!!,mainViewData)
        }else{
            Spacer(modifier = Modifier.width(8.dp).height(15.dp))
//            Box(
//                modifier = Modifier
//                    .width(8.dp)
//                    .height(15.dp)
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(Color.Red),
//            )
        }
        if (res.first){
            min += res.second!!.durationMinutes
        }else{
            min += 1
        }
    }
}
@Composable
fun TaskBox(task: Task, mainViewData: MainViewData){
    val duration = task.durationMinutes * 8
    Box(
        modifier = Modifier
            .width(duration.dp)
            .height(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(mainViewData.getLevelColor(task.weight)),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = task.name,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 8.sp)
        )
    }
}
fun checkTaskAtTime(time: Int,tasks: List<Task>) : Pair<Boolean,Task?>{
    tasks.forEach { task ->
        if(task.getStartTime() == time) {
            return Pair(true, task)
        }
    }
    return Pair(false,null)
}
fun checkActiveTime(mainViewData: MainViewData): Int{
    var min = mainViewData.currentMinute.intValue
    println(min)
    while (min % 5 != 0){
        min += 1
        println(min)
    }
    return min
}


