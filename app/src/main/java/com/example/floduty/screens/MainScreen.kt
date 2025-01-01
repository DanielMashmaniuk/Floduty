package com.example.floduty.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.floduty.data.MainViewModel
import com.example.floduty.data.db.DatabaseProvider
import com.example.floduty.ui.theme.FlodutyTheme
import com.example.floduty.view_models.MainView

class MainScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = DatabaseProvider.getDatabase(this)
        val taskDao = database.taskDao()
        val mainViewModel = MainViewModel(taskDao)
        setContent {
            MainView(mainViewModel)
        }
    }
}