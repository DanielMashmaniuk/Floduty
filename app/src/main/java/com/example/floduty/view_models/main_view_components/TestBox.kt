package com.example.floduty.view_models.main_view_components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.floduty.R
import com.example.floduty.screens.MainViewData
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import kotlinx.coroutines.delay


@OptIn(ExperimentalMotionApi::class)
@Composable
fun TestBox(mainViewData: MainViewData) {
    if (mainViewData.isTestScreenVisible.value) {
        val context = LocalContext.current
        val motionScene = remember {
            context.resources.openRawResource(R.raw.test_motion)
                .bufferedReader().use { it.readText() }
        }

        val progress = remember { mutableStateOf(0f) }
        LaunchedEffect(Unit) {
            // Інтервал для анімації
            while (progress.value < 1f) {
                delay(16) // 60fps
                progress.value += 0.01f
            }
        }

        MotionLayout(
            motionScene = MotionScene(motionScene),
            modifier = Modifier.fillMaxSize(),
            progress = progress.value
        ) {
            val boxProperties = motionProperties(id = "box")

            Box(
                modifier = Modifier
                    .size(boxProperties.value.int("width").dp)
                    .background(Color.Blue)
            )
        }
    }
}


