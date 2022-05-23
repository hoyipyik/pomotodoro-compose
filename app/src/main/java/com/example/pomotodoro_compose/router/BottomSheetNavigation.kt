package com.example.pomotodoro_compose.router

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomotodoro_compose.components.addComponents.AddGroupTag
import com.example.pomotodoro_compose.components.addComponents.AddTask
import com.example.pomotodoro_compose.components.TaskDetail
import com.example.pomotodoro_compose.viewModel.StateViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetNavigation(
    navController: NavHostController,
    stateViewModel: StateViewModel,
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState
) {
    val currentRouterPath: String = stateViewModel.currentRouterPath
//    Log.i("/navigationpage", currentRouterPath)
    NavHost(navController = navController, startDestination = "blank"){
        composable("blank"){

        }
        composable("lazy"){
            Test()
        }
        composable("addtask"){
            AddTask(type = currentRouterPath, tasksViewModel = tasksViewModel, scope = scope, bottomSheetState = bottomSheetState)
        }
        composable("taskdetail"){
            TaskDetail(type = currentRouterPath, tasksViewModel = tasksViewModel, scope = scope, bottomSheetState = bottomSheetState)
        }
        composable("addgrouptag"){
            AddGroupTag()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Test(){
    LazyColumn {
        items(12) {
            ListItem(
                text = { Text("Item $it") },
                icon = {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Localized description"
                    )
                }
            )
        }
    }
}