package com.example.pomotodoro_compose.router

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomotodoro_compose.pages.TimeLine
import com.example.pomotodoro_compose.pages.Today
import com.example.pomotodoro_compose.viewModel.StateViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SubNavigation(
    navController: NavController,
    type: String,
    tasksViewModel: TasksViewModel,
    state: ModalBottomSheetState,
    scope: CoroutineScope,
    stateViewModel: StateViewModel,
) {
    val list = tasksViewModel.todoTasksList
    NavHost(navController = navController as NavHostController, startDestination = "today") {
        composable("timeline") { TimeLine(type = type, list = list) }
        composable("today") { Today(type = type, list = list, tasksViewModel = tasksViewModel, stateViewModel = stateViewModel,state = state, scope = scope) }
    }
}