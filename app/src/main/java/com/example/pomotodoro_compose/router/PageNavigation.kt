package com.example.pomotodoro_compose.router

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomotodoro_compose.pages.Account
import com.example.pomotodoro_compose.pages.Board
import com.example.pomotodoro_compose.pages.Todo
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PageNavigation(
    scope: CoroutineScope,
    navController: NavController,
    bottomSheetState: ModalBottomSheetState,
    tasksViewModel: TasksViewModel,
    stateViewModel: StateViewModel,
    groupTagViewModel: GroupTagViewModel,
    bottomSheetNavController: NavHostController
) {

    NavHost(navController = navController as NavHostController, startDestination = "account"){
        composable("board"){ Board(scope = scope, state = bottomSheetState, tasksViewModel = tasksViewModel, stateViewModel = stateViewModel, groupTagViewModel = groupTagViewModel, bottomSheetNavController = bottomSheetNavController) }
        composable("todo"){ Todo(scope = scope, state = bottomSheetState, tasksViewModel = tasksViewModel, stateViewModel = stateViewModel, bottomSheetNavController = bottomSheetNavController)}
        composable("account"){ Account(scope = scope, state = bottomSheetState, tasksViewModel = tasksViewModel, stateViewModel = stateViewModel, groupTagViewModel = groupTagViewModel)}
    }
}