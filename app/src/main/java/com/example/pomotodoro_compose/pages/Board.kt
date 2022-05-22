package com.example.pomotodoro_compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomotodoro_compose.components.GroupTagListContainer
import com.example.pomotodoro_compose.components.TasksContainer
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Board(scope: CoroutineScope, state: ModalBottomSheetState) {
    val tasksViewModel: TasksViewModel = viewModel()
    val type: String = "board"
    val list = tasksViewModel.boardTasksList.toMutableStateList()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GroupTagListContainer()
        TasksContainer(list = list, type = type, tasksViewModel = tasksViewModel)
    }
}