package com.example.pomotodoro_compose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.pages.TimeLine
import com.example.pomotodoro_compose.pages.Today
import com.example.pomotodoro_compose.viewModel.TasksViewModel

@Composable
fun SubNavigation(
    navController: NavController,
    type: String,
) {
    val tasksViewModel: TasksViewModel = viewModel()
    val list = tasksViewModel.todoTasksList
    NavHost(navController = navController as NavHostController, startDestination = "today") {
        composable("timeline") { TimeLine(type = type, list = list) }
        composable("today") { Today(type = type, list = list, tasksViewModel = tasksViewModel) }
    }
}