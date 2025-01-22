package com.example.floduty.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String?,
    val notes: String?,
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val startDate: String,
    val endDate: String,
    val weight: Int,
    val completeStatus: String
) {
    val durationMinutes: Int
        get() = (endHour * 60 + endMinute) - (startHour * 60 + startMinute)

    fun getStartTime(): Int{
        return startHour * 60 + startMinute
    }
    companion object {
        fun createTask(
            n: String,
            descr: String,
            notesList: List<String> = listOf(""),
            sHour: Int,
            sMinute: Int,
            startDate: String,
            eHour: Int,
            endMinute: Int,
            endDate: String,
            weight: Int,
            completeStatus: String,
            onResult: (Task) -> Unit
        ){
            var notesFormat = ""
            for (note in notesList){
                notesFormat = "$notesFormat/$note"
            }
            val newTask = Task(
                name = n,
                description = descr.ifEmpty { "none" },
                notes = notesFormat.ifEmpty { "none" },
                startHour = sHour,
                startMinute = sMinute,
                startDate = startDate,
                endHour = eHour,
                endMinute = endMinute,
                endDate = endDate,
                weight = weight,
                completeStatus = completeStatus
            )
            onResult(newTask)
        }
    }
}
