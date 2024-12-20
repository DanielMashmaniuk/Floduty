package com.example.floduty.view_models.main_view_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.floduty.R
import com.example.floduty.ui.theme.Palette

@Composable
fun NavButtons(palette: Palette, isCalendarVisible: MutableState<Boolean>) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(bottomEnd = 50.dp))
            .background(palette.primaryColor),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = { isCalendarVisible.value = !isCalendarVisible.value },
            modifier = Modifier.size(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Next Month",
                tint = palette.mainBG,
                modifier = Modifier
                    .size(48.dp)
                    .padding(0.dp, 0.dp, 10.dp, 10.dp)
            )
        }
    }
}