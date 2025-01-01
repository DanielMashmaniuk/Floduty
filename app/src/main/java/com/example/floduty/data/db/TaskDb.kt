package com.example.floduty.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.floduty.data.models.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}