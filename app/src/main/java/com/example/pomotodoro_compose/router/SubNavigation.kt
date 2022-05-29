package com.example.pomotodoro_compose.router

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomotodoro_compose.pages.TimeLine
import com.example.pomotodoro_compose.pages.Today
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
) {
    val list by tasksViewModel.todoTasksList.observeAsState()
    NavHost(navController = navController as NavHostController, startDestination = "today") {
        composable("timeline") { list?.let { it1 -> TimeLine(type = type, list = it1) } }
        composable("today") { list?.let { it1 -> Today(type = type, list = it1, tasksViewModel = tasksViewModel, stateViewModel = stateViewModel,state = state, scope = scope) } }
    }
}