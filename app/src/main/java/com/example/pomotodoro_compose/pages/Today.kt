package com.example.pomotodoro_compose.pages

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.pomotodoro_compose.container.TasksContainer
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.viewModel.StateViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Today(
    type: String,
    list: MutableList<TasksData>,
    tasksViewModel: TasksViewModel,
      
    scope: CoroutineScope,
    state: ModalBottomSheetState,
    stateViewModel: StateViewModel
) {
    TasksContainer(
        list = list,
        type = type,
        tasksViewModel = tasksViewModel,
        bottomSheetState = state,
        scope = scope,
        stateViewModel = stateViewModel
    )
}