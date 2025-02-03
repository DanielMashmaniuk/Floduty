@file:OptIn(ExperimentalLayoutApi::class)

package com.example.floduty.view_models.main_view_components.main_content_components.nav_functions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
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
import com.example.floduty.screens.CNAScreenData
import com.example.floduty.screens.MainViewData
import com.example.floduty.data.models.DateAndTime
import com.example.floduty.data.models.Task
import com.example.floduty.ui.theme.Palette
import com.example.floduty.ui.theme.palette
import com.example.floduty.view_models.main_view_components.main_content_components.LevelBox
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateNewTaskBox(mainViewData: MainViewData) {
    val isClickedAName = remember { mutableStateOf(false) }
    val screenData = CNAScreenData(mainViewData)


    AnimatedVisibility(
        visible = mainViewData.isCreateActivityWindowVisible.value,
        enter = fadeIn() + expandVertically(),  // Ефект появи
        exit = fadeOut() + shrinkVertically()   // Ефект зникнення
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() }, // Вимкнення ефекту
                    indication = null
                ) {
                    if (screenData.isTimeSwitcherWindowVisible.value.first != "none") {
                        screenData.isTimeSwitcherWindowVisible.value = Pair("none", screenData.startDate)
                    }
                    if (screenData.isNotePanelVisible.value) {
                        screenData.isNotePanelVisible.value = false
                    }
                    if (mainViewData.isCalendarVisible.value){
                        mainViewData.isCalendarVisible.value = false
                    }
                }
                .background(palette.dark),
        ) {

            //main column container
            Column(
                Modifier
                    .height(getScreenHeight().dp)
                .verticalScroll(rememberScrollState()),
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
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
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
                                    color = screenData.color.value,
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            val animatedWidth = animateDpAsState(
                                targetValue = if (isClickedAName.value) 90.dp else 80.dp,
                                label = ""
                            )
                            val animatedHeight = animateDpAsState(
                                targetValue = if (isClickedAName.value) 35.dp else 30.dp,
                                label = ""
                            )
                            val coroutineScope = rememberCoroutineScope()
                            Box(
                                modifier = Modifier
                                    .height(animatedHeight.value)
                                    .width(animatedWidth.value)
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(screenData.color.value)
                                    .padding(5.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    )
                                    {
                                        isClickedAName.value = true
                                        screenData.nameActivity.value = changeActivity(screenData.nameActivity.value)
                                        coroutineScope.launch {
                                            delay(300)
                                            isClickedAName.value = false
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = screenData.nameActivity.value,
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
                                    if (screenData.isNotePanelVisible.value) {
                                        screenData.isNotePanelVisible.value = false
                                    }
                                    if (screenData.isTimeSwitcherWindowVisible.value.first != "none") {
                                        screenData.isTimeSwitcherWindowVisible.value =
                                            Pair("none", screenData.startDate)
                                    }
                                    mainViewData.isCreateActivityWindowVisible.value =
                                        !mainViewData.isCreateActivityWindowVisible.value
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.more_unfold),
                                    contentDescription = "back",
                                    tint = screenData.color.value,
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
                        mainViewData,
                        screenData
                    )
                }
                // create button
                val isClickedCButton = remember { mutableStateOf(false) }
                val animatedCornerSize = animateDpAsState(
                    targetValue = if (isClickedCButton.value) 2.dp else 16.dp,
                    label = ""
                )
                val animatedWidth = animateDpAsState(
                    targetValue = if (isClickedAName.value) 330.dp else 300.dp,
                    label = ""
                )
                val coroutineScope = rememberCoroutineScope()
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        Modifier
                            .width(animatedWidth.value)
                            .height(50.dp)
                            .padding(5.dp)
                            .clip(CutCornerShape(topStart = animatedCornerSize.value, topEnd = animatedCornerSize.value))
                            .background(screenData.color.value)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            )
                            {
                                isClickedCButton.value = true
                                coroutineScope.launch {
                                    delay(300)
                                    isClickedCButton.value = false
                                }
                                if (screenData.titleActivity.value.isEmpty()){
                                    mainViewData.showQuickMessage("Name area is empty")
                                    return@clickable
                                }
                                if (screenData.textDifference.value == "Invalid Time"){
                                    mainViewData.showQuickMessage("Time duration is invalid")
                                    return@clickable
                                }

                                Task.createTask(
                                    screenData.titleActivity.value,
                                    screenData.descriptionActivity.value,
                                    screenData.notes,
                                    screenData.startDate.value.hour,
                                    screenData.startDate.value.minutes,
                                    screenData.startDate.value.getDateDatabaseFormat(),
                                    screenData.endDate.value.hour,
                                    screenData.endDate.value.minutes,
                                    screenData.endDate.value.getDateDatabaseFormat(),
                                    screenData.currentLevel.intValue,
                                    screenData.checkIsComplete()
                                ){
                                    coroutineScope.launch {
                                        mainViewData.addNewTaskToDB(it)
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Add a new ${screenData.nameActivity.value.lowercase()} to schedule",
                            color = palette.mainBG,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            Box(
                modifier = Modifier.height(getScreenHeight().dp),
                contentAlignment = Alignment.Center
            ) {
                TimeSwitcher(mainViewData, screenData) {
                    if (it.first == "F") {
                        screenData.startDate.value = it.second
                        screenData.isTimeSwitcherWindowVisible.value = Pair("none", screenData.startDate)
                    } else {
                        screenData.endDate.value = it.second
                        screenData.isTimeSwitcherWindowVisible.value = Pair("none", screenData.startDate)
                    }
                }
                WriteNotePanel(screenData)
            }
        }
    }
}
@Composable
fun ActivityInfo(
    mainViewData: MainViewData,
    screenData: CNAScreenData,
    )
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val textWidth = with(LocalDensity.current) {
            (screenData.titleActivity.value.length * 9).dp // 12.dp - базова ширина одного символу
        }
        val textHeight = remember { mutableStateOf(50.dp) }
        val descriptionLines = remember { mutableIntStateOf(0) }
        val isFocusedTitle = remember { mutableStateOf(false) }
        val focusRequesterTitle = remember { FocusRequester() }
        val isFocusedDscp= remember { mutableStateOf(false) }
        val focusRequesterDscp = remember { FocusRequester() }
        val lineHeight = 16.sp
        BasicTextField(
            value = screenData.titleActivity.value,
            onValueChange = { if (it.length <= 50){
                val textValue = it.replace("\n", "")
                screenData.titleActivity.value = textValue
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
                    color = screenData.color.value,
                    shape = RoundedCornerShape(10.dp))
                .background(if (isFocusedTitle.value) palette.dark else screenData.color.value),

            textStyle = TextStyle(
                fontSize = 14.sp,
                color = if (isFocusedTitle.value) screenData.color.value else palette.mainBG,
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
                    if (screenData.titleActivity.value.isEmpty()) {
                        Text(
                            text = "Type title...",
                            color = if (isFocusedTitle.value) screenData.color.value else palette.mainBG,
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
                    screenData.currentLevel.intValue = switchLevel(screenData.currentLevel.intValue)
                    coroutineScope.launch {
                        delay(300)
                        isClicked.value = false
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            LevelBox(screenData.currentLevel.intValue,palette,mainViewData)
        }
        TitleInfo("Description")
        BasicTextField(
            value = screenData.descriptionActivity.value,
            onValueChange = { newText ->
                val isWithinMaxLength = newText.length <= 300
                val isDeleting = newText.length < screenData.descriptionActivity.value.length
                val isWithinMaxLines = descriptionLines.intValue <= 12 || isDeleting

                if (isWithinMaxLength && isWithinMaxLines) {
                    screenData.descriptionActivity.value = newText
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
                    color = screenData.color.value,
                    shape = RoundedCornerShape(10.dp))
                .background(if (isFocusedDscp.value) palette.dark else screenData.color.value),
            textStyle = TextStyle(
                color = if (isFocusedDscp.value) screenData.color.value else palette.mainBG,
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
                    if (screenData.descriptionActivity.value.isEmpty()) {
                        Text(
                            text = "Type description...",
                            color = if (isFocusedDscp.value) screenData.color.value else palette.mainBG,
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

        TitleInfo("Duration")
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .width(50.dp)
                .height(30.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(screenData.color.value),
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
                color = screenData.color.value,
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
                    text = screenData.startDate.value.getDateTxtFormat(mainViewData),
                    color = screenData.color.value,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                IconButton(
                    onClick = {
                        if (screenData.isNotePanelVisible.value){
                            screenData.isNotePanelVisible.value = false
                        }
                        screenData.isTimeSwitcherWindowVisible.value = Pair("F",screenData.startDate)
                              },
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .padding(0.dp)
                        .background(screenData.color.value)
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
                    text = mainViewData.getTimeFormat(screenData.startDate.value.minutes,screenData.startDate.value.hour),
                    color = screenData.color.value,
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
                .background(screenData.color.value),
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
                color = screenData.color.value,
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
                    text = screenData.endDate.value.getDateTxtFormat(mainViewData),
                    color = screenData.color.value,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                IconButton(
                    onClick = {
                        if (screenData.isNotePanelVisible.value){
                            screenData.isNotePanelVisible.value = false
                        }
                        screenData.isTimeSwitcherWindowVisible.value = Pair("T",screenData.endDate) },
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .padding(0.dp)
                        .background(screenData.color.value)
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
                    text = mainViewData.getTimeFormat(screenData.endDate.value.minutes,screenData.endDate.value.hour),
                    color = screenData.color.value,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }

        val textDifferenceWidth = with(LocalDensity.current) {
            (screenData.textDifference.value.length * 9).dp // 12.dp - базова ширина одного символу
        }
        Box(modifier = Modifier
            .height(30.dp)
            .width(textDifferenceWidth.coerceAtLeast(90.dp))
            .clip(CutCornerShape(50.dp))
            .background(screenData.color.value),
            contentAlignment = Alignment.Center
        ){

            Text(
                text = screenData.textDifference.value,
                color = palette.mainBG,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        TitleInfo("Notes")
        Box(Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(palette.lightGaryBG),
        ) {
            NotesList(screenData)
        }
    }
}

@Composable
fun NotesList(screenData: CNAScreenData) {
    val notes = remember { screenData.notes }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        notes.forEachIndexed { index, note ->
            key(note) {  // Гарантує збереження стану для кожного елемента
                val isVisible = remember { mutableStateOf(true) }

                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = slideInHorizontally(initialOffsetX = { 300 }) + fadeIn(),
                    exit = slideOutHorizontally(targetOffsetX = { -300 }) + fadeOut()
                ) {
                    NoteBox(note) {
                        isVisible.value = false
                    }

                    LaunchedEffect(isVisible.value) {
                        if (!isVisible.value) {
                            delay(300)
                            if (index < screenData.notes.size) {
                                screenData.notes.removeAt(index)
                            }
                        }
                    }
                }
            }
        }

        IconButton(
            onClick = {
                if (screenData.isTimeSwitcherWindowVisible.value.first != "none") {
                    screenData.isTimeSwitcherWindowVisible.value = Pair("none", screenData.startDate)
                }
                screenData.isNotePanelVisible.value = !screenData.isNotePanelVisible.value
            },
            modifier = Modifier
                .size(25.dp)
                .clip(RoundedCornerShape(50.dp))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add_svgrepo_com),
                contentDescription = "add note",
                tint = palette.orangeColor,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun WriteNotePanel(screenData: CNAScreenData){
    val noteText = remember { mutableStateOf("") }
    val textWidth = with(LocalDensity.current) {
        (noteText.value.length * 15).dp // 12.dp - базова ширина одного символу
    }
    AnimatedVisibility(
        visible = screenData.isNotePanelVisible.value,
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
                        color = palette.orangeColor,
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
                                    color = palette.orangeLightColor,
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
                        if (noteText.value.isNotEmpty() && !screenData.notes.contains(noteText.value)){
                            screenData.notes.add(noteText.value)
                        }
                        screenData.isNotePanelVisible.value = !screenData.isNotePanelVisible.value
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
fun NoteBox(note: String, onDeletedNote: (String) -> Unit) {
    val isVisible = remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isVisible.value,
        enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
    ) {
        val textNoteWidth = with(LocalDensity.current) {
            (note.length * 12).dp
        }
        Box(
            Modifier
                .width(textNoteWidth.coerceAtLeast(80.dp).coerceAtMost(150.dp))
                .height(25.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(palette.orangeColor),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp),
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
                    onClick = {
                        isVisible.value = false
                        onDeletedNote(note)
                    },
                    modifier = Modifier
                        .size(16.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .padding(0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = "delete note",
                        tint = palette.mainBG,
                        modifier = Modifier
                            .size(13.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TimeSwitcher(
    mainViewData: MainViewData,
    screenData: CNAScreenData,
    onTimeSelected: (Pair<String,DateAndTime>) -> Unit
) {

    val year = remember { mutableIntStateOf(screenData.isTimeSwitcherWindowVisible.value.second.value.year) }
    val month = remember { mutableIntStateOf(screenData.isTimeSwitcherWindowVisible.value.second.value.month) }
    val day = remember { mutableIntStateOf(screenData.isTimeSwitcherWindowVisible.value.second.value.day) }
    val hours = remember { mutableIntStateOf(screenData.isTimeSwitcherWindowVisible.value.second.value.hour) }
    val minutes = remember { mutableIntStateOf(screenData.isTimeSwitcherWindowVisible.value.second.value.minutes) }
    AnimatedVisibility(
        visible = if (screenData.isTimeSwitcherWindowVisible.value.first == "none") false else true,
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
                    ChoosedSection(2100, year, mainViewData)
                    ChoosedSection(13, month, mainViewData, true, false)
                    ChoosedSection(
                        mainViewData.getDaysInMonth(year.intValue, month.intValue) + 1,
                        day,
                        mainViewData,
                        containZero = false
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ChoosedSection(24, hours, mainViewData)
                        Text(
                            text = ":",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = palette.whiteColor
                            )
                        )
                        ChoosedSection(60, minutes, mainViewData)
                    }
                }
                Box(Modifier.fillMaxWidth().padding(5.dp).height(30.dp), contentAlignment = Alignment.Center) {
                    Row {
                        IconButton(onClick = {
                            onTimeSelected(
                                Pair(screenData.isTimeSwitcherWindowVisible.value.first,screenData.isTimeSwitcherWindowVisible.value.second.value)
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
                                Pair(screenData.isTimeSwitcherWindowVisible.value.first,DateAndTime(
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
fun ChoosedSection(endIdx: Int, value : MutableState<Int>,mainViewData: MainViewData, isMonth : Boolean = false, containZero: Boolean = true){
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
            text = if (isMonth) mainViewData.getMonthsNameByNumber(value.value) else text,
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
fun TitleInfo(text: String){
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

@Composable
fun getScreenHeight(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}