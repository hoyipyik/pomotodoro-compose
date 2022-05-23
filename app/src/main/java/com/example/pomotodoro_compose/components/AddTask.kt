package com.example.pomotodoro_compose.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.pomotodoro_compose.router.Test
import com.example.pomotodoro_compose.viewModel.TasksViewModel

@Composable
fun AddTask(type: String, modifier: Modifier, tasksViewModel: TasksViewModel) {
    if(type == "todo"){
        AddTodoTask(type = type, modifier = modifier, tasksViewModel = tasksViewModel)
    }else{
        AddBoardTask(type = type, modifier = modifier, tasksViewModel = tasksViewModel)
    }
}

@Composable
fun AddTodoTask(type: String, modifier: Modifier, tasksViewModel: TasksViewModel) {
    Text(
        text = "TODO",
        modifier = modifier
    )
}

@Composable
fun AddBoardTask(type: String, modifier: Modifier, tasksViewModel: TasksViewModel) {
    Text(
        text = "BOARD",
        modifier = modifier
    )
}