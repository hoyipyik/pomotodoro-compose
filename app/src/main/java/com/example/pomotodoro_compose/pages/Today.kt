package com.example.pomotodoro_compose.pages

import androidx.compose.runtime.Composable
import com.example.pomotodoro_compose.container.TasksContainer
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.viewModel.TasksViewModel

@Composable
fun Today(type: String, list: MutableList<TasksData>, tasksViewModel: TasksViewModel) {
    TasksContainer(list, type, tasksViewModel)
}