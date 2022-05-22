package com.example.pomotodoro_compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomotodoro_compose.container.BlankContainer
import com.example.pomotodoro_compose.container.GroupTagListContainer
import com.example.pomotodoro_compose.container.TasksContainer
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Board(scope: CoroutineScope, state: ModalBottomSheetState) {
    val tasksViewModel: TasksViewModel = viewModel()
    val type: String = "board"
    val list = tasksViewModel.boardTasksList
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GroupTagListContainer()
        if(list.size >= 0)
            TasksContainer(list = list, type = type, tasksViewModel = tasksViewModel)
        else
            BlankContainer()
    }
}