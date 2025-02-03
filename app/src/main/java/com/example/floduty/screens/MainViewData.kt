package com.example.floduty.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.floduty.data.models.Task
import com.example.floduty.data.db.TaskDao
import com.example.floduty.data.models.DateAndTime
import com.example.floduty.ui.theme.Palette
import com.example.floduty.ui.theme.palette
import com.example.floduty.view_models.main_view_components.CurrentDayBox
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MainViewData(private val taskDao: TaskDao) : ViewModel() {
    var currentMonth = mutableIntStateOf(getCurrentMonth())
    var currentYear = mutableIntStateOf(getCurrentYear())
    var currentDay = mutableIntStateOf(getCurrentDay())
    val currentHour = mutableIntStateOf(getCurrentHour())
    val currentMinute = mutableIntStateOf(getCurrentMinute())
    val tasksGroupsInHourLines = mutableStateListOf(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1)


    //VISIBILITIES
    val isCreateActivityWindowVisible = mutableStateOf(false)
    val isCalendarVisible = mutableStateOf(false)
    private val isWaitingScreenVisible = mutableStateOf(false)
    val isQuickMessageScreenVisible = mutableStateOf(false)

    val quickMessageState= mutableStateOf(QMInfo())
    private val messageQueue = mutableListOf<QMInfo>() // Черга повідомлень
    private var isProcessingQueue = false // Флаг для перевірки обробки черги

    private val nameMonthByNumber = mapOf(
        1 to "January",
        2 to "February",
        3 to "March",
        4 to "April",
        5 to "May",
        6 to "June",
        7 to "July",
        8 to "August",
        9 to "September",
        10 to "October",
        11 to "November",
        12 to "December"
    )
    private val nameLevelByNumber = mapOf(
        1 to "Simple",
        2 to "Easy",
        3 to "Medium",
        4 to "Hard",
        5 to "Extreme"
    )
    private val colorLevelByNumber :  Map<Int, Color> = mapOf(
        1 to palette.simpleLevelColor,
        2 to palette.easyColor,
        3 to palette.mediumColor,
        4 to palette.hardColor,
        5 to palette.extremeColor
    )
    val tasks = mutableStateOf<List<Task>>(emptyList()) // Використовуємо immutable List

    fun getLevelName(level: Int): String =
        nameLevelByNumber[level] ?: "Unknown"

    fun getLevelColor(level: Int): Color =
        colorLevelByNumber[level] ?: Color(0xFF4D6651)

    fun getMonthsNameByNumber(number: Int): String {
        return nameMonthByNumber[number] ?: "Invalid Month"
    }

    fun setNextMonth() {
        var month = currentMonth.intValue
        var year = currentYear.intValue

        if (month == 12) {
            month = 1
            year += 1
        } else {
            month += 1
        }

        currentMonth.intValue = month
        currentYear.intValue = year
    }

    fun setPreviousMonth() {
        var month = currentMonth.intValue
        var year = currentYear.intValue

        if (month == 1) {
            month = 12
            year -= 1
        } else {
            month -= 1
        }

        currentMonth.intValue = month
        currentYear.intValue = year
    }
    fun setCurrentDay(){
        currentMonth.intValue = getCurrentMonth()
        currentYear.intValue = getCurrentYear()
    }
    fun isCurrentDay(year: Int,month: Int,day: Int): Boolean{
        val currentMonth = getCurrentMonth()
        val currentYear = getCurrentYear()
        val currentDay = getCurrentDay()
        return year == currentYear && month == currentMonth && day == currentDay
    }
    fun getNextDate(
        d: Int = currentDay.intValue,
        m: Int = currentMonth.intValue,
        y: Int = currentYear.intValue,
        nd: Int = 0,
        numbersFormat: Boolean = false
    ): DateAndTime {
        // Ініціалізуємо початкову дату
        val startDate = LocalDate.of(y, m, d)

        // Список для зберігання дат
        val list = mutableListOf<DateAndTime>()

        // Генеруємо наступні дати
        if (nd > 0) {
            (1..nd).forEach { offset ->
                val newDate = startDate.plusDays(offset.toLong())
                list.add(
                    DateAndTime(
                        year = newDate.year,
                        month = newDate.monthValue,
                        day = newDate.dayOfMonth,
                        hour = currentHour.intValue,
                        minutes = currentMinute.intValue
                    )
                )
            }
        } else {
            list.add(DateAndTime(year = y, month = m, day = d))
        }

        // Повертаємо останню дату
        return list.last().let { date ->
            if (numbersFormat) {
                date
            } else {
                convertDateFormat(date)
            }
        }
    }
    fun getTimeFormat(m: Int = currentMinute.intValue, h: Int = currentHour.intValue): String {
        val hour = if (h<10) "0$h" else h.toString()
        val minute = if (m<10) "0$m" else m.toString()
        return "$hour:$minute"
    }
    private fun convertDateFormat(date: DateAndTime): DateAndTime {
        // Налаштовуємо потрібний формат
        return DateAndTime(
            year = date.year,
            month = date.month,
            day = date.day,
            hour = date.hour,
            minutes = date.minutes
        )
    }
    fun calculateTimeDifference(startDate: DateAndTime, endDate: DateAndTime): String {
        // Формат дати
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

        // Парсимо дати
        val start = LocalDateTime.parse(startDate.getPatternFormat(), formatter)
        val end = LocalDateTime.parse(endDate.getPatternFormat(), formatter)

        // Обчислення різниці
        val totalMinutes = ChronoUnit.MINUTES.between(start, end)
        val totalHours = totalMinutes / 60
        val days = totalHours / 24
        val months = ChronoUnit.MONTHS.between(start.withDayOfMonth(1), end.withDayOfMonth(1))
        val years = months / 12
        val remainingMonths = months % 12
        val remainingDays = days % 30
        val remainingHours = totalHours % 24
        val remainingMinutes = totalMinutes % 60

        // Функція для отримання правильної форми
        fun pluralize(value: Long, singular: String, plural: String): String {
            return if (value == 1L) "$value $singular" else "$value $plural"
        }

        // Формування результату
        return when {
            years > 0 && remainingMonths > 0 -> "${pluralize(years, "year", "years")}, ${pluralize(remainingMonths, "month", "months")}"
            years > 0 -> pluralize(years, "year", "years")
            months > 0 && remainingDays > 0 -> "${pluralize(months, "month", "months")}, ${pluralize(remainingDays, "day", "days")}"
            months > 0 -> pluralize(months, "month", "months")
            days > 0 && remainingHours > 0 -> "${pluralize(days, "day", "days")}, ${pluralize(remainingHours, "hour", "hours")}"
            days > 0 -> pluralize(days, "day", "days")
            remainingHours > 0 && remainingMinutes > 0 -> "${pluralize(remainingHours, "hour", "hours")}, ${pluralize(remainingMinutes, "minute", "minutes")}"
            remainingHours > 0 -> pluralize(remainingHours, "hour", "hours")
            remainingMinutes > 0 -> pluralize(remainingMinutes, "minute", "minutes")
            else -> "Invalid Time"
        }
    }


    fun getDaysInMonth(year: Int, month: Int): Int {
        return YearMonth.of(year, month).lengthOfMonth()
    }
    fun fetchTasks(year: Int,month: Int,day: Int){
        viewModelScope.launch {
            tasks.value = (getTasksOnDay(year,month,day))
            if (tasks.value.isEmpty()){
                showQuickMessage("No tasks here....")
            }
        }
    }
    //{--Task Area---------------------------------------------------------------------------------------------
    suspend fun addNewTaskToDB(task: Task) {
        try {
            taskDao.insertTask(task)
            showQuickMessage("Task has been added", palette.primaryColor1)
            val nowDay = "${currentYear.intValue}-${"%02d".format((currentMonth.intValue))}-${"%02d".format((currentDay.intValue))}"
            if (task.startDate <= nowDay && task.endDate >= nowDay){
                fetchTasks(currentYear.intValue,currentMonth.intValue,currentDay.intValue)
            }
            isCreateActivityWindowVisible.value = false
        } catch (e: Exception) {
            showQuickMessage("Task hasn't been added")
            throw e
        } finally {
            isWaitingScreenVisible.value = false
        }
    }
    private suspend fun getTasksOnDay(year: Int, month: Int, day: Int): MutableList<Task>{
        try {
            val targetTime = "$year-${"%02d".format((month))}-${"%02d".format((day))}"
            return taskDao.getTasksOnDay(targetTime).toMutableList()
        } catch (e: Exception){
            showQuickMessage("No tasks were fetched.")
            throw e
        }
    }
    fun fetchAllTask() {
        viewModelScope.launch {
            try {
                val tasksList = taskDao.getAllTasks()
                tasksList.forEach {
                    Log.d(it.name,it.toString())
                }
            } catch (e: Exception) {
                Log.e("getting T Error", e.message.toString())
                throw e
            } finally {
                isWaitingScreenVisible.value = false
            }
        }
    }
    fun setNewDate(year: Int,month: Int,day: Int){
        currentYear.intValue = year
        currentMonth.intValue = month
        currentDay.intValue = day

    }
    //--}Task Area---------------------------------------------------------------------------------------------

    fun showQuickMessage(
        message: String = "Something wrong",
        background: Color = palette.hardColor,
        time: Long = 2000
    ) {
        // Додаємо повідомлення до черги
        messageQueue.add(QMInfo(message, background))

        // Якщо черга ще не обробляється, запускаємо обробку
        if (!isProcessingQueue) {
            processMessageQueue(time)
        }
    }
    private fun processMessageQueue(time: Long) {
        isProcessingQueue = true // Встановлюємо флаг, що черга обробляється

        viewModelScope.launch {
            while (messageQueue.isNotEmpty()) {
                // Беремо перше повідомлення з черги
                val currentMessage = messageQueue.removeAt(0)

                // Встановлюємо повідомлення для показу
                quickMessageState.value = currentMessage
                isQuickMessageScreenVisible.value = true

                // Затримка на показ повідомлення
                delay(time)

                // Ховаємо повідомлення
                isQuickMessageScreenVisible.value = false

                // Затримка перед показом наступного (якщо потрібна)
                delay(time / 4)
            }
            isProcessingQueue = false // Завершили обробку черги
        }
    }
}

fun getCurrentMonth(): Int {
    val currentDate = LocalDate.now()
    return currentDate.monthValue
}

fun getCurrentYear(): Int {
    val currentDate = LocalDate.now()
    return currentDate.year
}
fun getCurrentDay(): Int{
    val currentDate = LocalDate.now()
    return currentDate.dayOfMonth
}
fun getCurrentMinute(): Int {
    val currentTime = LocalTime.now() // Отримуємо поточний час
    return currentTime.minute // Повертаємо хвилину
}
fun getCurrentHour(): Int {
    val currentTime = LocalTime.now() // Отримуємо поточний час
    return currentTime.hour // Повертаємо хвилину
}
class QMInfo(
    val message : String = "Something Wrong",
    val colorBG : Color = palette.hardColor,
    val textColor : Color = palette.mainBG
){

}