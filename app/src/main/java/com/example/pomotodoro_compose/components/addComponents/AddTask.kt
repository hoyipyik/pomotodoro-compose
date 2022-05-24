package com.example.pomotodoro_compose.components.addComponents

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.ui.theme.Purple500
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTask(
    type: String,
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope
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
            tasksViewModel = tasksViewModel,
            scope = scope,
            bottomSheetState = bottomSheetState
        )
    }
}



