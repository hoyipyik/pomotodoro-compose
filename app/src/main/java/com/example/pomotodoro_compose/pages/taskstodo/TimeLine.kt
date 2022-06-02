package com.example.pomotodoro_compose.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomotodoro_compose.components.items.TimelineItem
import com.example.pomotodoro_compose.container.BlankContainer
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import com.example.pomotodoro_compose.ui.theme.*


@Composable
fun TimeLine(type: String, tasksViewModel: TasksViewModel) {
    val timeline = tasksViewModel.timelineList
    if (timeline.size > 0) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Blue200)
                        .align(CenterVertically)
                ) {
                    Spacer(modifier = Modifier.fillMaxSize(0.14f))
                    Text(text = tasksViewModel.doneTodoWorkNum.toString(), fontSize = 33.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), color = Color.White)
                    Text(text = "Done", textAlign = TextAlign.Center, fontSize = 15.sp, modifier = Modifier.fillMaxWidth(), color = Color.White)
                }
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Blue500)
                        .align(CenterVertically)
                ) {
                    Spacer(modifier = Modifier.fillMaxSize(0.14f))
                    Text(text = tasksViewModel.unfinishedTodoWorkNum.toString(), fontSize = 33.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), color = Color.White)
                    Text(text = "Uncheck", textAlign = TextAlign.Center, fontSize = 15.sp, modifier = Modifier.fillMaxWidth(),color = Color.White)
                }
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Blue700)
                        .align(CenterVertically)
                ) {
                    Spacer(modifier = Modifier.fillMaxSize(0.14f))
                    Text(text = tasksViewModel.overdueTaskNum.toString(), fontSize = 33.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), color = Color.White)
                    Text(text = "Overdue", textAlign = TextAlign.Center, fontSize = 15.sp, modifier = Modifier.fillMaxWidth(), color = Color.White)
                }
            }

            LazyColumn(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp).clip(RoundedCornerShape(6.dp))
            ) {
                items(timeline) {
                    TimelineItem(item = it)
//                Text(text = it)
                }
            }
        }
    } else
        BlankContainer(type = "timeline")
}