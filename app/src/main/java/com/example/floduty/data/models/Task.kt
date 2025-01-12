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
    val isCompleted: Boolean
) {
    val durationMinutes: Int
        get() = (endHour * 60 + endMinute) - (startHour * 60 + startMinute)

    fun getStartTime(): Int{
        return startHour * 60 + startMinute
    }
}
