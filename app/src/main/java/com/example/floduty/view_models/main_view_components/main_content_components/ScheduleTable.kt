package com.example.floduty.view_models.main_view_components.main_content_components

import android.util.Log
import androidx.compose.compiler.plugins.kotlin.lower.fastForEach
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    val hours = (0..23)

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
            Spacer(modifier = Modifier.height(35.dp))
            hours.forEach { hour ->
                val height = mainViewData.tasksGroupsInHourLines[hour] * 15
                if (height > 16){
                    Log.d("h",mainViewData.tasksGroupsInHourLines[hour].toString())
                }
                val colorText =
                    if (hour == mainViewData.currentHour.intValue) palette.orangeColor else palette.primaryColor
                Box(
                    modifier = Modifier.width(28.dp).height(height.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        //modifier = Modifier.padding(bottom = 2.dp),
                        text = hour.toString().padStart(2, '0'),
                        color = colorText,
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
                modifier = Modifier.fillMaxWidth().height(35.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                minutes.forEach { minute ->
                    val mText = if (minute < 10) "0${minute}" else minute.toString()
                    val colorText =
                        if (checkActiveTime(mainViewData) == minute) palette.orangeColor else palette.primaryColor
                    Text(
                        modifier = Modifier.width(20.dp),
                        text = mText,
                        color = colorText,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                hours.forEach { hour ->
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SetHourLine(hour, mainViewData)
                    }
                }
            }
        }
    }
}
@Composable
fun SetHourLine(hour: Int, mainViewData: MainViewData) {
    val tasksListAtHour = remember(hour, mainViewData.tasks.value) {
        getTasksByMinuteAtHour(mainViewData,hour)
    }

    if (tasksListAtHour.isEmpty()) {
        Box(modifier = Modifier.height(15.dp)) {

        }
    }

    Column(
        modifier = Modifier.height(IntrinsicSize.Min).widthIn(min = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val groupedTasks = remember(tasksListAtHour) {
            groupOverlappingTasks(tasksListAtHour.sortedBy { it.startMinute })
        }
        if (groupedTasks.size > 1){
            mainViewData.tasksGroupsInHourLines[hour] = groupedTasks.size
            Log.d("hl",hour.toString())
        }
        groupedTasks.forEach { tasksInRow ->
            Row {
                var currentMinute = 0

                tasksInRow.forEach { taskInfo ->
                    // Add spacer for gap between current minute and task start
                    if (currentMinute < taskInfo.startMinute) {
                        val gapWidth = (taskInfo.startMinute - currentMinute) * 8
                        Spacer(
                            modifier = Modifier
                                .width(gapWidth.dp)
                                .height(15.dp)
                        )
                    }

                    // Display the task
                    TaskBox(taskInfo, mainViewData, checkTrulyStartTime(mainViewData,taskInfo.task,hour),
                        checkTrulyEndTime(mainViewData,taskInfo.task,hour)
                    )

                    // Update current minute to end of this task
                    currentMinute = taskInfo.endMinute
                }

                // Fill remaining space if any
                if (currentMinute < 60) {
                    val remainingWidth = (60 - currentMinute) * 8
                    Spacer(
                        modifier = Modifier
                            .width(remainingWidth.dp)
                            .height(15.dp)
                    )
                }
            }
        }
    }
}

private fun groupOverlappingTasks(sortedTasks: List<TaskDisplayInfo>): List<List<TaskDisplayInfo>> {
    if (sortedTasks.isEmpty()) return emptyList()

    val groups = mutableListOf<MutableList<TaskDisplayInfo>>()
    val currentGroup = mutableListOf<TaskDisplayInfo>()
    groups.add(currentGroup)

    sortedTasks.forEach { task ->
        // Try to add task to an existing row where it doesn't overlap
        val added = groups.any { group ->
            if (canAddToGroup(group, task)) {
                group.add(task)
                true
            } else {
                false
            }
        }

        // If task couldn't be added to any existing row, create a new row
        if (!added) {
            val newGroup = mutableListOf(task)
            groups.add(newGroup)
        }
    }

    return groups
}

private fun canAddToGroup(group: List<TaskDisplayInfo>, task: TaskDisplayInfo): Boolean {
    return group.none { existingTask ->
        // Check if tasks overlap
        !(task.endMinute <= existingTask.startMinute ||
                task.startMinute >= existingTask.endMinute)
    }
}

@Composable
fun TaskBox(taskInfo: TaskDisplayInfo, mainViewData: MainViewData,isExactStart: Boolean, isExactEnd: Boolean){
    val constWidth = 8
    val boxWith = taskInfo.visibleMinutes * constWidth

    val startBorder = if (isExactStart) 10 else 0
    val endBorder = if (isExactEnd) 10 else 0

    Box(
        modifier = Modifier
            .width(boxWith.dp)
            .height(15.dp)
            .clip(RoundedCornerShape(
                topStart = startBorder.dp, bottomStart = startBorder.dp,
                topEnd = endBorder.dp, bottomEnd = endBorder.dp)
            )
            .background(mainViewData.getLevelColor(taskInfo.task.weight)),
        contentAlignment = Alignment.Center
    ){
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (boxWith > 50 && isExactStart) taskInfo.task.getTimeSFormat(true) else "",
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 8.sp)
            )
            Text(
                text = if (taskInfo.task.name.length * 8 > boxWith) "${taskInfo.task.name.take(boxWith % 8)}..." else taskInfo.task.name,
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 10.sp)
            )
            Text(
                text = if (boxWith > 50 && isExactEnd) taskInfo.task.getTimeSFormat(false) else "",
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 8.sp)
            )
        }

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
fun getTasksByMinuteAtHour(mainViewData: MainViewData,hour: Int): List<TaskDisplayInfo> {
    val hourStart = hour * 60      // Початок години у хвилинах
    val hourEnd = hourStart + 59   // Кінець години у хвилинах

    return mainViewData.tasks.value.mapNotNull { task ->
        val taskStartTime = task.startHour * 60 + task.startMinute
        val taskEndTime = task.endHour * 60 + task.endMinute
        val today = "${"%02d".format(mainViewData.currentYear.intValue)}-" +
                "${"%02d".format(mainViewData.currentMonth.intValue)}-" +
                "%02d".format(mainViewData.currentDay.intValue)

        val isTaskInThisDay = task.startDate <= today && task.endDate >= today
        val isTaskDuringHour = taskStartTime <= hourEnd && taskEndTime >= hourStart
        if (hour == 1){
            Log.d("TASK CHECKING","${task.name}:$isTaskDuringHour,$isTaskInThisDay, - - $today")
        }
        if (!isTaskInThisDay || !isTaskDuringHour) return@mapNotNull null  // Якщо завдання не в межах дня чи години, пропускаємо

        val startMinute = if (taskStartTime < hourStart) 0 else taskStartTime - hourStart
        val endMinute = if (taskEndTime > hourEnd) 59 else taskEndTime - hourStart

        val visibleMinutes = endMinute - startMinute   // Скільки хвилин видно


        TaskDisplayInfo(task, startMinute, endMinute, visibleMinutes)
    }
}


fun getActiveHours(mainViewData: MainViewData): MutableList<String>{
    var i = 0
    val tList = mutableListOf<String>()
    while (i < 24) {
        mainViewData.tasks.value.forEach {
            val time = "${"%02d".format((it.startHour))}:${"%02d".format((it.startMinute))}"

            if (it.startHour == 2) {

            }
        }
    }
    return tList
}
fun checkActiveTime(mainViewData: MainViewData): Int{
    var min = mainViewData.currentMinute.intValue
    while (min % 5 != 0){
        min += 1
    }
    return min
}
fun checkTrulyStartTime(mainViewData: MainViewData,task: Task,hour: Int): Boolean{
    val isThisDay =
        "%d-%02d-%02d".format(mainViewData.currentYear.intValue, mainViewData.currentMonth.intValue, mainViewData.currentDay.intValue) == task.startDate

    val isThatHour = hour == task.startHour
    return isThatHour && isThisDay
}
fun checkTrulyEndTime(mainViewData: MainViewData,task: Task, hour: Int): Boolean{
    val isThisDay =
        "%d-%02d-%02d".format(mainViewData.currentYear.intValue, mainViewData.currentMonth.intValue, mainViewData.currentDay.intValue) == task.endDate

    val isThatHour = hour == task.endHour || (task.endHour - hour == 1 && task.endMinute == 0)
    return isThatHour && isThisDay
}
data class TaskDisplayInfo(
    val task: Task,
    val startMinute: Int,
    val endMinute: Int,
    val visibleMinutes: Int,
)


