package com.example.pomotodoro_compose.router

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomotodoro_compose.components.addComponents.AddGroupTag
import com.example.pomotodoro_compose.components.addComponents.AddTask
import com.example.pomotodoro_compose.components.pomodoro.Pomodoro
import com.example.pomotodoro_compose.components.taskDetails.BoardTaskDetail
import com.example.pomotodoro_compose.components.taskDetails.TaskDetail
import com.example.pomotodoro_compose.container.EditGroupTagContainer
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetNavigation(
    stateViewModel: StateViewModel,
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    groupTagViewModel: GroupTagViewModel,
    bottomSheetNavController: NavHostController
) {
    val currentRouterPath: String = stateViewModel.currentRouterPath
    val currentRouteBottomSheetPath: String = stateViewModel.currentRouteBottomSheetPath
    when (currentRouterPath) {
        "board" -> {
            when (currentRouteBottomSheetPath) {
                "addgrouptag" -> AddGroupTag(
                    type = currentRouterPath,
                    groupTagViewModel = groupTagViewModel,
                    scope = scope,
                    bottomSheetState = bottomSheetState
                )
                "editgrouptag" -> EditGroupTagContainer(
                    groupTagViewModel = groupTagViewModel,
                    bottomSheetState = bottomSheetState,
                    tasksViewModel = tasksViewModel
                )
                "pomodoro" -> Pomodoro(
                    tasksViewModel = tasksViewModel,
                    stateViewModel = stateViewModel,
                    scope = scope,
                    bottomSheetState = bottomSheetState
                )
                else -> {
                    NavHost(navController = bottomSheetNavController, startDestination = "blank") {
                        composable("blank") {

                        }
                        composable("addtask") {
                            AddTask(
                                type = currentRouterPath,
                                tasksViewModel = tasksViewModel,
                                scope = scope,
                                bottomSheetState = bottomSheetState,
                                groupTagViewModel = groupTagViewModel
                            )
                        }
                        composable("taskdetail") {
                            TaskDetail(
                                type = currentRouterPath,
                                tasksViewModel = tasksViewModel,
                                scope = scope,
                                bottomSheetState = bottomSheetState,
                                stateViewModel = stateViewModel,
                                groupTagViewModel = groupTagViewModel
                            )
                        }
                    }
                }

            }

        }
        "todo" -> when (currentRouteBottomSheetPath) {
            "addtask" -> AddTask(
                type = currentRouterPath,
                tasksViewModel = tasksViewModel,
                scope = scope,
                bottomSheetState = bottomSheetState,
                groupTagViewModel = groupTagViewModel
            )
            "taskdetail" -> TaskDetail(
                type = currentRouterPath,
                tasksViewModel = tasksViewModel,
                scope = scope,
                bottomSheetState = bottomSheetState,
                stateViewModel = stateViewModel,
                groupTagViewModel = groupTagViewModel
            )
            "addgrouptag" -> AddGroupTag(
                type = currentRouterPath,
                groupTagViewModel = groupTagViewModel,
                scope = scope,
                bottomSheetState = bottomSheetState
            )
            "editgrouptag" -> EditGroupTagContainer(
                groupTagViewModel = groupTagViewModel,
                bottomSheetState = bottomSheetState,
                tasksViewModel = tasksViewModel
            )
            "pomodoro" -> Pomodoro(
                tasksViewModel = tasksViewModel,
                stateViewModel = stateViewModel,
                scope = scope,
                bottomSheetState = bottomSheetState
            )

        }
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
