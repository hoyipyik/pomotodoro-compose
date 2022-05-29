package com.example.pomotodoro_compose.components.addComponents

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTask(
    type: String,
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    groupTagViewModel: GroupTagViewModel
) {
    if (type == "todo") {
        AddTodoTask(
            type = type,
            modifier = modifier,
            tasksViewModel = tasksViewModel,
            scope = scope,
            bottomSheetState = bottomSheetState
        )
    } else {
        AddBoardTask(
            type = type,
            modifier = modifier,
            groupTagViewModel = groupTagViewModel,
            tasksViewModel = tasksViewModel,
            scope = scope,
            bottomSheetState = bottomSheetState
        )
    }
}



