package com.example.floduty.view_models.main_view_components.main_content_components.nav_functions

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.floduty.R
import com.example.floduty.ui.theme.Palette
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        //main column container
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
                // label column
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        // main title
                        Text(
                            text = "Create a new ${nameActivity.value}",
                            color = getActivityColorByName(nameActivity.value, palette),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    // button task
                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        var isClicked = remember { mutableStateOf(false) }
                        val animatedWidth = animateDpAsState(targetValue = if (isClicked.value) 90.dp else 80.dp,
                            label = ""
                        )
                        val animatedHeight = animateDpAsState(targetValue = if (isClicked.value) 35.dp else 30.dp,
                            label = ""
                        )
                        val coroutineScope = rememberCoroutineScope()
                        Box(
                            modifier = Modifier
                                .height(animatedHeight.value)
                                .width(animatedWidth.value)
                                .clip(RoundedCornerShape(50.dp))
                                .background(getActivityColorByName(nameActivity.value, palette))
                                .padding(5.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                )
                                {
                                    isClicked.value = true
                                    nameActivity.value = changeActivity(nameActivity.value)
                                    coroutineScope.launch {
                                        delay(300)
                                        isClicked.value = false
                                    }
                                },
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
            Box(Modifier.fillMaxWidth().padding(start = 5.dp)){
                ActivityInfo(palette,nameActivity.value)
            }
        }
    }
}
@Composable
fun ActivityInfo(palette: Palette,nameActivity: String){
    var titleActivity = remember { mutableStateOf("") }

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                Modifier
                    .height(30.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(getActivityColorByName(nameActivity, palette)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Title",
                    color = palette.mainBG,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Box(
                Modifier
                    .height(30.dp)
                    .width(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = ":",
                    color = palette.whiteColor,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            val textWidth = with(LocalDensity.current) {
                (titleActivity.value.length * 12).dp // 12.dp - базова ширина одного символу
            }
            TextField(
                value = titleActivity.value,
                onValueChange = { titleActivity.value = it },
                modifier = Modifier
                    .width(textWidth.coerceAtLeast(80.dp))
                    .height(40.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(getActivityColorByName(nameActivity, palette)),
                placeholder = { Text(
                    text = "Type Title",
                    textStyle = TextStyle(
                    color = palette.mainBG,
                    fontSize = 12.sp, // Розмір тексту, який підходить для обмеженої висоти
                    lineHeight = 18.sp, // Лінійна висота для візуальної чіткості
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold
                ),) },
                textStyle = TextStyle(
                    color = palette.mainBG,
                    fontSize = 12.sp, // Розмір тексту, який підходить для обмеженої висоти
                    lineHeight = 18.sp, // Лінійна висота для візуальної чіткості
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
            )
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