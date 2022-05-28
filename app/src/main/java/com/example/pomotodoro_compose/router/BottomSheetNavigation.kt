package com.example.pomotodoro_compose.router

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomotodoro_compose.components.addComponents.AddGroupTag
import com.example.pomotodoro_compose.components.addComponents.AddTask
import com.example.pomotodoro_compose.components.TaskDetail
import com.example.pomotodoro_compose.components.pomodoro.Pomodoro
import com.example.pomotodoro_compose.container.EditGroupTagContainer
import com.example.pomotodoro_compose.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.viewModel.StateViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetNavigation(
    stateViewModel: StateViewModel,
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
      
    groupTagViewModel: GroupTagViewModel
) {
    val currentRouterPath: String = stateViewModel.currentRouterPath

    when(val currentRouteBottomSheetPath: String = stateViewModel.currentRouteBottomSheetPath){
        "addtask" ->  AddTask(type = currentRouterPath, tasksViewModel = tasksViewModel, scope = scope, bottomSheetState = bottomSheetState, groupTagViewModel = groupTagViewModel)
        "taskdetail" -> TaskDetail(type = currentRouterPath, tasksViewModel = tasksViewModel, scope = scope, bottomSheetState = bottomSheetState, stateViewModel = stateViewModel, groupTagViewModel = groupTagViewModel)
        "addgrouptag" -> AddGroupTag(type = currentRouterPath, groupTagViewModel = groupTagViewModel, scope = scope, bottomSheetState = bottomSheetState)
        "editgrouptag" -> EditGroupTagContainer(groupTagViewModel = groupTagViewModel, bottomSheetState = bottomSheetState, tasksViewModel = tasksViewModel)
        "pomodoro" -> Pomodoro(tasksViewModel = tasksViewModel, stateViewModel = stateViewModel, scope = scope, bottomSheetState = bottomSheetState)

    }
//    Log.i("/navigationpage", currentRouterPath)
//    NavHost(navController = navController, startDestination = "blank") {
//        composable("blank"){
//
//        }
//        composable("addtask"){
//            AddTask(type = currentRouterPath, tasksViewModel = tasksViewModel, scope = scope, bottomSheetState = bottomSheetState, groupTagViewModel = groupTagViewModel)
//        }
//        composable("taskdetail"){
//            TaskDetail(type = currentRouterPath, tasksViewModel = tasksViewModel, scope = scope, bottomSheetState = bottomSheetState,  , stateViewModel = stateViewModel, groupTagViewModel = groupTagViewModel)
//        }
//        composable("addgrouptag"){
//            AddGroupTag(type = currentRouterPath, groupTagViewModel = groupTagViewModel, scope = scope, bottomSheetState = bottomSheetState)
//        }
//        composable("editgrouptag"){
//            EditGroupTagContainer(groupTagViewModel = groupTagViewModel, bottomSheetState = bottomSheetState, tasksViewModel = tasksViewModel)
//        }
//        composable("pomodoro"){
//            Pomodoro(tasksViewModel = tasksViewModel, stateViewModel = stateViewModel, scope = scope, bottomSheetState = bottomSheetState)
//        }
//    }
}
