package com.example.pomotodoro_compose.router

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomotodoro_compose.pages.Account
import com.example.pomotodoro_compose.pages.Board
import com.example.pomotodoro_compose.pages.Todo
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PageNavigation(
    scope: CoroutineScope,
    navController: NavController,
    bottomSheetState: ModalBottomSheetState
) {
    val tasksViewModel : TasksViewModel = viewModel()
    NavHost(navController = navController as NavHostController, startDestination = "board"){
        composable("board"){ Board(scope = scope, state = bottomSheetState, tasksViewModel = tasksViewModel) }
        composable("todo"){ Todo(scope = scope, state = bottomSheetState, tasksViewModel = tasksViewModel)}
        composable("account"){ Account(scope = scope, state = bottomSheetState)}
    }
}