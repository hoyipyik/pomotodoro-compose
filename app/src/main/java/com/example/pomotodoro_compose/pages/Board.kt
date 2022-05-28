package com.example.pomotodoro_compose.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.pomotodoro_compose.container.BlankContainer
import com.example.pomotodoro_compose.container.GroupTagListContainer
import com.example.pomotodoro_compose.container.TasksContainer
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.viewModel.StateViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Board(
    scope: CoroutineScope,
    state: ModalBottomSheetState,
    tasksViewModel: TasksViewModel,
    stateViewModel: StateViewModel,
    groupTagViewModel: GroupTagViewModel
) {
    val type: String = "board"
    var list = tasksViewModel.boardTasksList
    val filteredList: MutableList<TasksData> = mutableListOf()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        LaunchedEffect(tasksViewModel.changeFlag) {
            list = tasksViewModel.boardTasksList
            tasksViewModel.restoreChangeFlag()
        }
        list.forEachIndexed{ index, data ->
            for (item in data.groupTag){
                if(item == tasksViewModel.selectedGroupTag){
                    filteredList.add(list[index])
                }
            }
        }
        GroupTagListContainer(groupTagViewModel = groupTagViewModel, tasksViewModel = tasksViewModel, type = type, scope = scope, bottomSheetState = state, stateViewModel = stateViewModel)
        if(list.size >= 0)
            TasksContainer(list = filteredList, type = type, tasksViewModel = tasksViewModel, scope = scope, bottomSheetState = state, stateViewModel = stateViewModel)
        else
            BlankContainer()
    }
}