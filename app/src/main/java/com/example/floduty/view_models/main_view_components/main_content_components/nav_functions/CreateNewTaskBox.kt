package com.example.floduty.view_models.main_view_components.main_content_components.nav_functions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.floduty.R
import com.example.floduty.ui.theme.Palette

@Composable
fun CreateNewTaskBox(palette: Palette) {
    val nameActivity = remember { mutableStateOf("Task") }

    Box(
        modifier = Modifier
            .height(600.dp)
            .width(350.dp)
            .shadow(8.dp, shape = RoundedCornerShape(20.dp)) // Тінь із закругленими краями
            .clip(RoundedCornerShape(20.dp))
            .background(palette.dark)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Create a new ${nameActivity.value}",
                            color = getActivityColorByName(nameActivity.value, palette),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .height(30.dp)
                                .width(80.dp)
                                .clip(RoundedCornerShape(50.dp))
                                .background(getActivityColorByName(nameActivity.value, palette))
                                .padding(5.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                )
                                { nameActivity.value = changeActivity(nameActivity.value) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = nameActivity.value,
                                color = palette.mainBG,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
fun changeActivity(name: String): String{
    return if (name == "Task") "Event" else "Task"
}
fun getActivityColorByName(name: String,palette: Palette): Color{
    return if (name == "Task"){
        palette.primaryColor
    }else{
        palette.orangeColor
    }
}