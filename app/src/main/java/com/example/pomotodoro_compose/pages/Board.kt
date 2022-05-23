package com.example.pomotodoro_compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.pomotodoro_compose.container.BlankContainer
import com.example.pomotodoro_compose.container.GroupTagListContainer
import com.example.pomotodoro_compose.container.TasksContainer
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Board(
    scope: CoroutineScope,
    state: ModalBottomSheetState,
    tasksViewModel: TasksViewModel,
    bottomSheetNavController: NavHostController,
    currentRouteBottomSheet: String?
) {
    val type: String = "board"
    val list = tasksViewModel.boardTasksList
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        GroupTagListContainer()
        if(list.size >= 0)
            TasksContainer(list = list, type = type, tasksViewModel = tasksViewModel, scope = scope, bottomSheetState = state, currentRouteBottomSheet = currentRouteBottomSheet, bottomSheetNavController = bottomSheetNavController)
        else
            BlankContainer()
    }
}