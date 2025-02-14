package com.example.floduty.view_models.main_view_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.floduty.ui.theme.palette

@Composable
fun WeekBox(day: String) {
    Box(
        modifier = Modifier
            .width(30.dp)
            .clip(RoundedCornerShape(50)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day,
            color = palette.primaryColor,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold // Додаємо жирний шрифт
            )
        )
    }
}
@Composable
fun DayBox(day: Int, onClicked : (Int) -> Unit) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(RoundedCornerShape(50))
            .background(palette.orangeColor),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { onClicked(day) },
            modifier = Modifier
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = palette.transparentColor // Прозорий фон кнопки
            ),
            contentPadding = PaddingValues(0.dp) // Прибираємо відступи за замовчуванням
        ) {
            Text(
                text = day.toString(),
                color = palette.mainBG,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold // Додаємо жирний шрифт
                )
            )
        }

    }
}
@Composable
fun ActiveDayBox(day: Int, isCurrent:Boolean = false) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(CutCornerShape(10.dp))
            .background(if (isCurrent) palette.primaryColor else palette.sageColor,),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.toString(),
            color = if (isCurrent) palette.orangeColor else palette.mainBG,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun InactiveDayBox(day: Int) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(RoundedCornerShape(50))
            .background(palette.lightGaryBG),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = palette.transparentColor // Прозорий фон кнопки
            ),
            contentPadding = PaddingValues(0.dp) // Прибираємо відступи за замовчуванням
        ) {
            Text(
                text = day.toString(),
                color = palette.mainBG,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold // Додаємо жирний шрифт
                )
            )
        }
    }
}
@Composable
fun CurrentDayBox(day: Int,onClicked : (Int) -> Unit) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .clip(RoundedCornerShape(50))
            .background(palette.primaryColor),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { onClicked(day) },
            modifier = Modifier
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = palette.transparentColor // Прозорий фон кнопки
            ),
            contentPadding = PaddingValues(0.dp) // Прибираємо відступи за замовчуванням
        ) {
            Text(
                text = day.toString(),
                color = palette.mainBG,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold // Додаємо жирний шрифт
                )
            )
        }
    }
}