package com.example.pomotodoro_compose.pages

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.example.pomotodoro_compose.container.BlankContainer
import com.example.pomotodoro_compose.container.TasksContainer
import com.example.pomotodoro_compose.data.entity.TasksData
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
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
    if (list.size > 0)
        TasksContainer(
            list = list,
            type = type,
            tasksViewModel = tasksViewModel,
            bottomSheetState = state,
            scope = scope,
            stateViewModel = stateViewModel
        )
    else
        BlankContainer(type = type)
}