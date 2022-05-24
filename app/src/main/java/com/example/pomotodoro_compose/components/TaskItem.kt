package com.example.pomotodoro_compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.viewModel.StateViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskItem(
    item: TasksData,
    type: String,
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    state: ModalBottomSheetState,
    bottomSheetNavController: NavHostController,
    stateViewModel: StateViewModel,
) {
    val id: String = item.id
    val toToday: Boolean = item.toToday
    val title: String = item.title
    val isChecked: Boolean = item.isChecked
    val priority: Boolean = item.priority
    var finishTime: String? = item.finishTime
    var checked by remember { mutableStateOf(isChecked) }
    var priorityFlag by remember { mutableStateOf(priority) }

    LaunchedEffect(tasksViewModel.changeFlag) {
        checked = tasksViewModel.getItem(id).isChecked
        priorityFlag = tasksViewModel.getItem(id).priority
        tasksViewModel.restoreChangeFlag()
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.Center)
                .height(70.dp)
                .padding(top = 0.dp, bottom = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable(onClick = {
                    priorityFlag = !priorityFlag
                    tasksViewModel.upgradeTask(
                        type = type,
                        name = "priority",
                        id = id,
                        value = priorityFlag
                    )
                })
                .background(MaterialTheme.colors.onSecondary),
            verticalAlignment = Alignment.CenterVertically

        ) {
            if (type == "board" && toToday)
                Box(
                    modifier = Modifier
                        .border(5.dp, MaterialTheme.colors.primary)
                        .width(5.dp)
                        .fillMaxHeight()
                )
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    tasksViewModel.upgradeTask(type = type, name = "isChecked", id = id, value = it)
                    checked = it
                },
                modifier = Modifier.padding(start = 4.dp)
            )
            if (priorityFlag)
                Text(
                    text = title, modifier = Modifier
                        .fillMaxWidth(0.82f)
                        .padding(start = 1.dp), fontWeight = FontWeight.Bold
                )
            else
                Text(
                    text = title, modifier = Modifier
                        .fillMaxWidth(0.82f)
                        .padding(start = 1.dp)
                )
            IconButton(
                onClick = {
                    bottomSheetNavController.navigate("taskdetail") {
                        popUpTo(stateViewModel.currentRouteBottomSheetPath) { inclusive = true }
                    }
                    stateViewModel.changeCurrentRouteBottomSheetPath("taskdetail")
                    tasksViewModel.sendId(id)
                    scope.launch {
                        state.show()
//                        state.animateTo(
//                            targetValue = ModalBottomSheetValue.HalfExpanded,
//                            anim = tween(
//                            durationMillis = 1000,
//                            delayMillis = 0
//                        ))
                    }
                },
                modifier = Modifier.padding(end = 4.dp)
            ) {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
        }
    }
}