package com.example.floduty.data

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.floduty.data.db.DatabaseProvider
import com.example.floduty.screens.MainViewData
import com.example.floduty.view_models.MainView

class MainScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = DatabaseProvider.getDatabase(this)
        val taskDao = database.taskDao()
        val mainViewData = MainViewData(taskDao)
        setContent {
            MainView(mainViewData)
        }
    }
}