package com.example.pomotodoro_compose.container

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.components.grouptag.GroupTagList
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GroupTagListContainer(
    tasksViewModel: TasksViewModel,
    stateViewModel: StateViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    type: String,
    groupTagViewModel: GroupTagViewModel
) {
    Box(modifier = Modifier.height(55.dp).padding(vertical = 10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(0.86f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.85f)) {
                GroupTagList(modifier = Modifier, groupTagViewModel = groupTagViewModel, tasksViewModel = tasksViewModel)
            }
            IconButton(onClick = {
//                bottomSheetNavController.navigate("editgrouptag") {
//                    popUpTo(stateViewModel.currentRouteBottomSheetPath) { inclusive = true }
//                }
                stateViewModel.changeCurrentRouteBottomSheetPath("editgrouptag")
                scope.launch { bottomSheetState.show() }
            }, modifier = Modifier.padding(start = 9.dp)) {
                Icon(Icons.Filled.Backspace, contentDescription = null)
            }
        }
    }
}


