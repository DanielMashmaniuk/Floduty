@file:OptIn(ExperimentalLayoutApi::class)

package com.example.floduty.view_models.main_view_components.main_content_components.nav_functions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.floduty.R
import com.example.floduty.data.MainViewModel
import com.example.floduty.data.models.DateAndTime
import com.example.floduty.ui.theme.Palette
import com.example.floduty.view_models.main_view_components.main_content_components.LevelBox
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateNewTaskBox(palette: Palette,mainViewModel: MainViewModel,isCreateActivityWindowVisible: MutableState<Boolean>) {
    val nameActivity = remember { mutableStateOf("Task") }

    val isTimeSwitcherWindowVisible = remember { mutableStateOf(Pair("none",mainViewModel.startDate)) }

    AnimatedVisibility(
        visible = isCreateActivityWindowVisible.value,
        enter = fadeIn() + expandVertically(),  // Ефект появи
        exit = fadeOut() + shrinkVertically()   // Ефект зникнення
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(palette.dark),
        ) {

            //main column container
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // label column
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Space -- button
                        Box(
                            Modifier.size(30.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            Box(
                                modifier = Modifier.width(130.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                // main title
                                Text(
                                    text = "Create a new ",
                                    color = getActivityColorByName(nameActivity.value, palette),
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            val isClicked = remember { mutableStateOf(false) }
                            val animatedWidth = animateDpAsState(
                                targetValue = if (isClicked.value) 90.dp else 80.dp,
                                label = ""
                            )
                            val animatedHeight = animateDpAsState(
                                targetValue = if (isClicked.value) 35.dp else 30.dp,
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
                        // cancel button
                        Box(
                            Modifier.width(30.dp).height(30.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            IconButton(
                                onClick = {
                                    if (mainViewModel.isNotePanelVisible.value){
                                        mainViewModel.isNotePanelVisible.value = false
                                    }
                                    if (isTimeSwitcherWindowVisible.value.first != "none"){
                                        isTimeSwitcherWindowVisible.value = Pair("none",mainViewModel.startDate)
                                    }
                                    isCreateActivityWindowVisible.value =
                                        !isCreateActivityWindowVisible.value
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.more_unfold),
                                    contentDescription = "back",
                                    tint = getActivityColorByName(nameActivity.value, palette),
                                    modifier = Modifier
                                        .size(28.dp)
                                )
                            }
                        }
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ActivityInfo(
                        palette,
                        nameActivity.value,
                        mainViewModel.startDate,
                        mainViewModel.endDate,
                        isTimeSwitcherWindowVisible,
                        mainViewModel
                    )
                }
            }

            Box (
                modifier = Modifier.height(getScreenHeight().dp),
                contentAlignment = Alignment.Center
            ) {
                TimeSwitcher(palette,mainViewModel,isTimeSwitcherWindowVisible){
                    if (it.first == "F") {
                        mainViewModel.startDate.value = it.second
                        isTimeSwitcherWindowVisible.value = Pair("none", mainViewModel.startDate)
                    }else{
                        mainViewModel.endDate.value = it.second
                        isTimeSwitcherWindowVisible.value = Pair("none", mainViewModel.startDate)
                    }
                }
                WriteNotePanel(palette,mainViewModel)
            }
        }
    }
}
@Composable
fun ActivityInfo(
    palette: Palette,
    nameActivity: String,
    startDate: MutableState<DateAndTime>,
    endDate:MutableState<DateAndTime>,
    isTimeSwitcherWindowVisible: MutableState<Pair<String,MutableState<DateAndTime>>>,
    mainViewModel: MainViewModel)
{
    val titleActivity = remember { mutableStateOf("") }
    val descriptionActivity = remember { mutableStateOf("") }



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val textWidth = with(LocalDensity.current) {
            (titleActivity.value.length * 9).dp // 12.dp - базова ширина одного символу
        }
        val textHeight = remember { mutableStateOf(50.dp) }
        val descriptionLines = remember { mutableIntStateOf(0) }
        val isFocusedTitle = remember { mutableStateOf(false) }
        val focusRequesterTitle = remember { FocusRequester() }
        val isFocusedDscp= remember { mutableStateOf(false) }
        val focusRequesterDscp = remember { FocusRequester() }
        val lineHeight = 16.sp
        BasicTextField(
            value = titleActivity.value,
            onValueChange = { if (it.length <= 50){
                val textValue = it.replace("\n", "")
                titleActivity.value = textValue
            } },
            modifier = Modifier
                .height(40.dp)
                .width(textWidth.coerceAtLeast(120.dp))
                .clip(RoundedCornerShape(10.dp))
                .focusRequester(focusRequesterTitle)
                .onFocusChanged { focusState ->
                    isFocusedTitle.value = focusState.isFocused
                }
                .border(
                    width = 2.dp,
                    color = getActivityColorByName(nameActivity,palette),
                    shape = RoundedCornerShape(10.dp))
                .background(if (isFocusedTitle.value) palette.dark else getActivityColorByName(nameActivity,palette)),

            textStyle = TextStyle(
                fontSize = 14.sp,
                color = if (isFocusedTitle.value) getActivityColorByName(nameActivity,palette) else palette.mainBG,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (titleActivity.value.isEmpty()) {
                        Text(
                            text = "Type title...",
                            color = if (isFocusedTitle.value) getActivityColorByName(nameActivity,palette) else palette.mainBG,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
        val isClicked = remember { mutableStateOf(false) }
        val animatedWidth = animateDpAsState(targetValue = if (isClicked.value) 90.dp else 80.dp,
            label = ""
        )
        val animatedHeight = animateDpAsState(targetValue = if (isClicked.value) 35.dp else 30.dp,
            label = ""
        )
        val currentLevel = remember { mutableIntStateOf(1) }

        val coroutineScope = rememberCoroutineScope()
        Box(
            modifier = Modifier
                .height(animatedHeight.value)
                .width(animatedWidth.value)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
                {
                    isClicked.value = true
                    currentLevel.intValue = switchLevel(currentLevel.intValue)
                    coroutineScope.launch {
                        delay(300)
                        isClicked.value = false
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            LevelBox(currentLevel.intValue,palette,mainViewModel)
        }
        TitleInfo("Description",palette)
        BasicTextField(
            value = descriptionActivity.value,
            onValueChange = { newText ->
                val isWithinMaxLength = newText.length <= 300
                val isDeleting = newText.length < descriptionActivity.value.length
                val isWithinMaxLines = descriptionLines.intValue <= 12 || isDeleting

                if (isWithinMaxLength && isWithinMaxLines) {
                    descriptionActivity.value = newText
                }
            },
            modifier = Modifier
                .height(textHeight.value)
                .width(300.dp)
                .clip(RoundedCornerShape(10.dp))
                .focusRequester(focusRequesterDscp)
                .onFocusChanged { focusState ->
                    isFocusedDscp.value = focusState.isFocused
                }
                .border(
                    width = 2.dp,
                    color = getActivityColorByName(nameActivity,palette),
                    shape = RoundedCornerShape(10.dp))
                .background(if (isFocusedDscp.value) palette.dark else getActivityColorByName(nameActivity,palette)),
            textStyle = TextStyle(
                color = if (isFocusedDscp.value) getActivityColorByName(nameActivity,palette) else palette.mainBG,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    if (descriptionActivity.value.isEmpty()) {
                        Text(
                            text = "Type description...",
                            color = if (isFocusedDscp.value) getActivityColorByName(nameActivity,palette) else palette.mainBG,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    innerTextField()
                }
            },
            onTextLayout = { textLayoutResult ->
                // Обчислюємо кількість рядків
                descriptionLines.intValue = textLayoutResult.lineCount
                val calculatedHeight = (lineHeight.value * descriptionLines.intValue).dp + 32.dp // Динамічна висота контейнера
                textHeight.value = maxOf(calculatedHeight, 50.dp) // Гарантуємо мінімум 50.dp
            }
        )

        TitleInfo("Duration",palette)
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .width(50.dp)
                .height(30.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(getActivityColorByName(nameActivity,palette)),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "FROM",
                    color = palette.mainBG,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Text(
                text = ":",
                color = getActivityColorByName(nameActivity,palette),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = startDate.value.getDateTxtFormat(mainViewModel),
                    color = getActivityColorByName(nameActivity,palette),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                IconButton(
                    onClick = {
                        if (mainViewModel.isNotePanelVisible.value){
                            mainViewModel.isNotePanelVisible.value = false
                        }
                        isTimeSwitcherWindowVisible.value = Pair("F",startDate)
                              },
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .padding(0.dp)
                        .background(getActivityColorByName(nameActivity,palette))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.switch_icon),
                        contentDescription = "switch date",
                        tint = palette.mainBG,
                        modifier = Modifier
                            .size(16.dp)
                    )
                }
                Text(
                    text = mainViewModel.getTimeFormat(startDate.value.minutes,startDate.value.hour),
                    color = getActivityColorByName(nameActivity,palette),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .width(50.dp)
                .height(30.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(getActivityColorByName(nameActivity,palette)),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "TO",
                    color = palette.mainBG,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Text(
                text = ":",
                color = getActivityColorByName(nameActivity,palette),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = endDate.value.getDateTxtFormat(mainViewModel),
                    color = getActivityColorByName(nameActivity,palette),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                IconButton(
                    onClick = {
                        if (mainViewModel.isNotePanelVisible.value){
                            mainViewModel.isNotePanelVisible.value = false
                        }
                        isTimeSwitcherWindowVisible.value = Pair("T",endDate) },
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .padding(0.dp)
                        .background(getActivityColorByName(nameActivity,palette))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.switch_icon),
                        contentDescription = "switch date",
                        tint = palette.mainBG,
                        modifier = Modifier
                            .size(16.dp)
                    )
                }
                Text(
                    text = mainViewModel.getTimeFormat(endDate.value.minutes,endDate.value.hour),
                    color = getActivityColorByName(nameActivity,palette),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
        val textDifference = remember(startDate.value, endDate.value) {
            derivedStateOf {
                mainViewModel.calculateTimeDifference(startDate.value, endDate.value)
            }
        }
        val textDifferenceWidth = with(LocalDensity.current) {
            (textDifference.value.length * 9).dp // 12.dp - базова ширина одного символу
        }
        Box(modifier = Modifier
            .height(30.dp)
            .width(textDifferenceWidth.coerceAtLeast(90.dp))
            .clip(RoundedCornerShape(50.dp))
            .background(getActivityColorByName(nameActivity,palette)),
            contentAlignment = Alignment.Center
        ){

            Text(
                text = textDifference.value,
                color = palette.mainBG,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        TitleInfo("Notes",palette)
        Box(Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(25.dp))
            .verticalScroll(rememberScrollState())
            .background(palette.lightGaryBG),
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                mainViewModel.notes.forEach { note ->
                    NoteBox(palette, note) {
                        mainViewModel.notes.removeAt(mainViewModel.notes.indexOf(it))
                    }
                }
                IconButton(
                    onClick = {
                        if (isTimeSwitcherWindowVisible.value.first != "none"){
                            isTimeSwitcherWindowVisible.value = Pair("none",mainViewModel.startDate)
                        }
                        mainViewModel.isNotePanelVisible.value = !mainViewModel.isNotePanelVisible.value
                              },
                    modifier = Modifier
                        .size(25.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .padding(0.dp)
                        .background(palette.orangeColor)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add_svgrepo_com),
                        contentDescription = "add note",
                        tint = palette.lightGaryBG,
                        modifier = Modifier
                            .size(14.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun WriteNotePanel(palette: Palette,mainViewModel: MainViewModel){
    val noteText = remember { mutableStateOf("") }
    val textWidth = with(LocalDensity.current) {
        (noteText.value.length * 15).dp // 12.dp - базова ширина одного символу
    }
    AnimatedVisibility(
        visible = mainViewModel.isNotePanelVisible.value,
        enter = fadeIn() + expandVertically(),  // Ефект появи
        exit = fadeOut() + shrinkVertically()   // Ефект зникнення
    ) {
        Box(Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(palette.lightGaryBG),
            contentAlignment = Alignment.Center
        ) {
            Row (
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = noteText.value,
                    onValueChange = {
                        if (it.length <= 35) {
                            val textValue = it.replace("\n", "")
                            noteText.value = textValue
                        }
                    },
                    modifier = Modifier
                        .height(40.dp)
                        .width(textWidth.coerceAtLeast(150.dp).coerceAtMost(300.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(palette.lightGaryBG),

                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = palette.mainBG,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (noteText.value.isEmpty()) {
                                Text(
                                    text = "Type note...",
                                    color = palette.mainBG,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                IconButton(
                    onClick = {
                        if (noteText.value.isNotEmpty()){
                            mainViewModel.notes.add(noteText.value)
                        }
                        mainViewModel.isNotePanelVisible.value = !mainViewModel.isNotePanelVisible.value
                        noteText.value = ""
                    },
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .padding(0.dp)
                        .background(palette.orangeColor)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add_svgrepo_com ),
                        contentDescription = "add note",
                        tint = palette.lightGaryBG,
                        modifier = Modifier
                            .size(16.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun NoteBox(palette: Palette,note: String, onDeletedNote: (String) -> Unit){
    val textNoteWidth = with(LocalDensity.current) {
        (note.length * 12).dp
    }
    Box(Modifier
        .width(textNoteWidth.coerceAtLeast(80.dp).coerceAtMost(150.dp))
        .height(25.dp)
        .clip(RoundedCornerShape(25.dp))
        .background(palette.orangeColor),
        contentAlignment = Alignment.Center
    ){
        Row(modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (note.length > 14) "${note.take(11)}..." else note,
                color = palette.mainBG,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            IconButton(
                onClick = { onDeletedNote(note) },
                modifier = Modifier
                    .size(16.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .padding(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = "switch date",
                    tint = palette.mainBG,
                    modifier = Modifier
                        .size(13.dp)
                )
            }
        }
    }
}
@Composable
fun TimeSwitcher(
    palette: Palette,
    mainViewModel: MainViewModel,
    data: MutableState<Pair<String,MutableState<DateAndTime>>>,
    onTimeSelected: (Pair<String,DateAndTime>) -> Unit
) {
    // Створення стану для збереження часу
    val year = remember { mutableIntStateOf(data.value.second.value.year) } // Початкове значення для годин
    val month = remember { mutableIntStateOf(data.value.second.value.month) } // Початкове значення для годин
    val day = remember { mutableIntStateOf(data.value.second.value.day) } // Початкове значення для годин
    val hours = remember { mutableIntStateOf(data.value.second.value.hour) } // Початкове значення для годин
    val minutes = remember { mutableIntStateOf(data.value.second.value.minutes) } // Початкове значення для хвилин
    AnimatedVisibility(
        visible = if (data.value.first == "none") false else true,
        enter = fadeIn() + expandVertically(),  // Ефект появи
        exit = fadeOut() + shrinkVertically()   // Ефект зникнення
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 10.dp)
                .shadow(8.dp, shape = RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(palette.lightGaryBG)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Вибір годин і хвилин
                Row(
                    Modifier.height(150.dp).padding(top = 30.dp, start = 15.dp, end = 15.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ChoosedSection(2100, year, palette, mainViewModel)
                    ChoosedSection(13, month, palette, mainViewModel, true, false)
                    ChoosedSection(
                        mainViewModel.getDaysInMonth(year.intValue, month.intValue) + 1,
                        day,
                        palette,
                        mainViewModel,
                        containZero = false
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ChoosedSection(24, hours, palette, mainViewModel)
                        Text(
                            text = ":",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = palette.whiteColor
                            )
                        )
                        ChoosedSection(60, minutes, palette, mainViewModel)
                    }
                }
                Box(Modifier.fillMaxWidth().padding(5.dp).height(30.dp), contentAlignment = Alignment.Center) {
                    Row {
                        IconButton(onClick = {
                            onTimeSelected(
                                Pair(data.value.first,data.value.second.value)
                            )
                        }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.close_square),
                                contentDescription = "Increase hours",
                                tint = palette.hardColor,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                        IconButton(onClick = {
                            onTimeSelected(
                                Pair(data.value.first,DateAndTime(
                                    year.intValue,
                                    month.intValue,
                                    day.intValue,
                                    hours.intValue,
                                    minutes.intValue
                                ))
                            )
                        }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.done_ring_round),
                                contentDescription = "Increase hours",
                                tint = palette.primaryColor,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun ChoosedSection(endIdx: Int,value : MutableState<Int>,palette: Palette,mainViewModel: MainViewModel,isMonth : Boolean = false,containZero: Boolean = true){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = {

            value.value = (value.value + 1) % endIdx
            if (!containZero && value.value == 0) {
                value.value = 1
            }

        }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_up),
                contentDescription = "Increase hours",
                tint = palette.whiteColor,
                modifier = Modifier
                    .size(18.dp)
            )
        }
        val text = if (containZero) value.value.toString().padStart(2, '0') else value.value.toString()
        Text(
            text = if (isMonth) mainViewModel.getMonthsNameByNumber(value.value) else text,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = palette.whiteColor
            )
        )
        IconButton(onClick = {
            value.value = if (containZero) {
                (value.value - 1 + endIdx) % endIdx
            } else {
                val v = (value.value - 1 + endIdx) % endIdx
                if (v == 0) endIdx - 1 else v
            }

        }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_down),
                contentDescription = "Decrease hours",
                tint = palette.whiteColor,
                modifier = Modifier
                    .size(18.dp)
            )
        }
    }
}
@Composable
fun TitleInfo(text: String,palette: Palette){
    Box(
        Modifier
            .height(30.dp)
            .width(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = palette.whiteColor,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
fun switchLevel(level: Int): Int{
    return if (level == 5) 1 else level + 1
}
fun changeActivity(name: String): String{
    return if (name == "Task") "Event" else "Task"
}
fun getActivityColorByName(name: String,palette: Palette): Color{
    return if (name == "Task"){
        palette.primaryColor
    }else{
        palette.eventColor
    }
}
@Composable
fun getScreenHeight(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}