package com.example.pomotodoro_compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pomotodoro_compose.components.TasksContainer
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Todo(scope: CoroutineScope, state: ModalBottomSheetState, navController: NavHostController) {
    val tasksViewModel: TasksViewModel = viewModel()
    val type: String = "todo"
    val list: MutableList<TasksData> = tasksViewModel.todoTasksList
    Column {
        Button(onClick = { scope.launch { state.show() } }) {
            Text("Click to show sheet")
        }
        TasksContainer(list, type)
    }
}