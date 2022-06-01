package com.example.pomotodoro_compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.pomotodoro_compose.components.items.TimelineItem
import com.example.pomotodoro_compose.container.BlankContainer
import com.example.pomotodoro_compose.data.entity.TasksData
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel


@Composable
fun TimeLine(type: String, tasksViewModel: TasksViewModel) {
    val timeline = tasksViewModel.timelineList
    if (timeline.size > 0) {
        Column {
            Row {
                Text(text = tasksViewModel.doneTodoWorkNum.toString())
                Text(text = tasksViewModel.unfinishedTodoWorkNum.toString())
                Text(text = tasksViewModel.overdueTaskNum.toString())
            }

            LazyColumn {
                items(timeline) {
                    TimelineItem(item = it)
//                Text(text = it)
                }
            }
        }
    } else
        BlankContainer(type = "timeline")
}