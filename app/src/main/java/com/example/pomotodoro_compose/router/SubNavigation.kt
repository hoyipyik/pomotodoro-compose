package com.example.pomotodoro_compose.router

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomotodoro_compose.pages.taskstodo.TimeLine
import com.example.pomotodoro_compose.pages.taskstodo.Today
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
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
    bottomSheetNavController: NavHostController,
) {
    var list = tasksViewModel.todoTasksList
    val timelineList = tasksViewModel.timelineList
    LaunchedEffect(tasksViewModel.changeFlag){
        list = tasksViewModel.todoTasksList
        tasksViewModel.restoreChangeFlag()
    }

    NavHost(navController = navController as NavHostController, startDestination = "today") {
        composable("timeline") { TimeLine( type = type, tasksViewModel = tasksViewModel) }
        composable("today") { Today(list = list, type = type, tasksViewModel = tasksViewModel, stateViewModel = stateViewModel,state = state, scope = scope, bottomSheetNavController = bottomSheetNavController) }
    }
}