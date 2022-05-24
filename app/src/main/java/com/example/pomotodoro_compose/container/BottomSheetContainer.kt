package com.example.pomotodoro_compose.container

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pomotodoro_compose.router.BottomSheetNavigation
import com.example.pomotodoro_compose.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.viewModel.StateViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContainer(
    navController: NavHostController,
    stateViewModel: StateViewModel,
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    bottomSheetNavController: NavHostController,
    groupTagViewModel: GroupTagViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.padding(6.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.15f)
            .height(3.dp)
            .border(2.dp, Color.LightGray)
            .clip(RoundedCornerShape(10.dp))
            .padding(vertical = 5.dp))
        BottomSheetNavigation(
            bottomSheetNavController = bottomSheetNavController,
            groupTagViewModel = groupTagViewModel,
            navController = navController,
            stateViewModel = stateViewModel,
            modifier = Modifier.fillMaxWidth(),
            tasksViewModel = tasksViewModel,
            scope = scope,
            bottomSheetState = bottomSheetState
        )
    }
}