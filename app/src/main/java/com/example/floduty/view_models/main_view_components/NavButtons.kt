package com.example.floduty.view_models.main_view_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.floduty.R
import com.example.floduty.screens.MainViewData
import com.example.floduty.ui.theme.Palette
import com.example.floduty.ui.theme.palette

@Composable
fun NavButtons( mainViewData: MainViewData) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(bottomEnd = 50.dp))
            .background(palette.primaryColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.calendar),
            contentDescription = "Next Month",
            tint = palette.mainBG,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, // Вимкнення ефекту
                    indication = null
                ) { mainViewData.isCalendarVisible.value = !mainViewData.isCalendarVisible.value }
                .size(36.dp)
                .padding(0.dp, 0.dp, 10.dp, 10.dp)
        )
    }
}