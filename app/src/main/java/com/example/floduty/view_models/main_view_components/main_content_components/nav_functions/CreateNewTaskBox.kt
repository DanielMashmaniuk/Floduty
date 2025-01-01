package com.example.floduty.view_models.main_view_components.main_content_components.nav_functions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.floduty.ui.theme.Palette

@Composable
fun CreateNewTaskBox(palette: Palette) {
    Box(
        modifier = Modifier
            .height(600.dp)
            .width(350.dp)
            .shadow(8.dp, shape = RoundedCornerShape(20.dp)) // Тінь із закругленими краями
            .clip(RoundedCornerShape(20.dp))
            .background(palette.dark),
        contentAlignment = Alignment.Center
    ){

    }
}