package com.example.pomotodoro_compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.pomotodoro_compose.router.SubNavigation
import com.example.pomotodoro_compose.components.TabBar
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Todo(
    scope: CoroutineScope,
    state: ModalBottomSheetState,
    tasksViewModel: TasksViewModel,
    stateViewModel: StateViewModel
) {
    val type: String = "todo"
    val navController = rememberNavController()
    Column {
        TabBar(navController = navController, tasksViewModel = tasksViewModel, stateViewModel = stateViewModel)
        SubNavigation(navController = navController, type = type, tasksViewModel = tasksViewModel,  stateViewModel = stateViewModel, scope = scope, state = state)
    }
}