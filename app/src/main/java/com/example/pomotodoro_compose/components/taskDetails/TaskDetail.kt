package com.example.pomotodoro_compose.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.example.pomotodoro_compose.components.taskDetails.BoardTaskDetail
import com.example.pomotodoro_compose.components.taskDetails.TodoTaskDetail
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskDetail(
    type: String,
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState
) {
    when (type) {
        "todo" -> TodoTaskDetail(tasksViewModel, scope, bottomSheetState, type = type)
        "board" -> BoardTaskDetail(tasksViewModel, scope, bottomSheetState, type = type)
    }
}