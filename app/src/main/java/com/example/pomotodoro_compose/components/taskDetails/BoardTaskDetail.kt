package com.example.pomotodoro_compose.components.taskDetails

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoardTaskDetail(
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    type: String
) {
    Text("Board")
}