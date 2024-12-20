package com.example.floduty.view_models.main_view_components.main_content_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.floduty.R
import com.example.floduty.data.MainViewModel
import com.example.floduty.ui.theme.Palette

@Composable
fun ScheduleTable(currentYear: Int, currentMonth : Int, currentDay: Int, palette: Palette, mainViewModel: MainViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
    ) {
        Box(
            modifier = Modifier
                .height(60.dp)
                .width(350.dp)
                .shadow(8.dp, shape = RoundedCornerShape(20.dp)) // Тінь із закругленими краями
                .clip(RoundedCornerShape(20.dp))
                .background(palette.secondBG),
            contentAlignment = Alignment.Center
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {  },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.clipboard_add),
                        contentDescription = "Next Month",
                        tint = palette.orangeColor,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
                IconButton(
                    onClick = {  },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.maximize_square_minimalistic),
                        contentDescription = "Next Month",
                        tint = palette.orangeColor,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
                IconButton(
                    onClick = {  },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.list_arrow_down),
                        contentDescription = "Next Month",
                        tint = palette.orangeColor,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
                IconButton(
                    onClick = {  },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = "Next Month",
                        tint = palette.orangeColor,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
                IconButton(
                    onClick = {  },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.check_read),
                        contentDescription = "Next Month",
                        tint = palette.orangeColor,
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun Schedule(){

}