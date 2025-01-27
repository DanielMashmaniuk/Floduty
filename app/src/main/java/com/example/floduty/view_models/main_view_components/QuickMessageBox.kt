package com.example.floduty.view_models.main_view_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.floduty.screens.CNAScreenData
import com.example.floduty.screens.MainViewData
import com.example.floduty.ui.theme.palette

@Composable
fun QuickMessageBox(mainViewData: MainViewData) {

    AnimatedVisibility(
        visible = mainViewData.isQuickMessageScreenVisible.value,
        enter = fadeIn() + expandHorizontally(),  // Ефект появи
        exit = fadeOut() + shrinkHorizontally()   // Ефект зникнення
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .height(40.dp)
                .shadow(8.dp, shape = RoundedCornerShape(12.dp)) // Тінь із закругленими краями
                .clip(RoundedCornerShape(12.dp))
                .background(mainViewData.quickMessageState.value.colorBG),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = mainViewData.quickMessageState.value.message,
                color = mainViewData.quickMessageState.value.textColor,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}