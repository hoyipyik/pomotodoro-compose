package com.example.pomotodoro_compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pomotodoro_compose.components.SubNavigation
import com.example.pomotodoro_compose.components.TabBar
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Todo(scope: CoroutineScope, state: ModalBottomSheetState) {
    val tasksViewModel: TasksViewModel = viewModel()
    val type: String = "todo"
    val list: MutableList<TasksData> = tasksViewModel.todoTasksList
    val navController = rememberNavController()
    Column {
        TabBar(navController = navController)
        SubNavigation(navController = navController, list = list, type = type)
    }
}