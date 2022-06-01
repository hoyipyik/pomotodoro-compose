package com.example.pomotodoro_compose.container

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.components.items.TaskItem
import com.example.pomotodoro_compose.data.entity.TasksData
import com.example.pomotodoro_compose.ui.theme.Purple200
import com.example.pomotodoro_compose.ui.theme.Teal200
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TasksContainer(
    list: MutableList<TasksData>,
    type: String,
    tasksViewModel: TasksViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,

    stateViewModel: StateViewModel
) {

    LazyColumn(
        modifier = Modifier.fillMaxHeight(0.92f)
    ) {
        items(list, {list: TasksData ->  list.id}) { item ->
            val state = rememberDismissState()
            if(state.isDismissed(DismissDirection.EndToStart)){
                tasksViewModel.deleteTask(type = type, id = item.id)
                LaunchedEffect(Unit) {
                    state.reset()
                }
                if(type == "todo"){
                    list.remove(item)
                }
            }
            if (state.isDismissed(DismissDirection.StartToEnd)) {
                tasksViewModel.upgradeTask(type = type, id = item.id, value = !item.toToday, name = "toToday")
                LaunchedEffect(Unit) {
                    state.reset()
                }
                if(type == "todo"){
                    list.remove(item)
                }
            }
            SwipeToDismiss(
                state = state,
                background = { SwipBackground(state = state) },
                dismissContent = {
                    TaskItem(item, type, tasksViewModel = tasksViewModel, scope = scope, state = bottomSheetState, stateViewModel = stateViewModel,  )
                },
                dismissThresholds = { direction ->
                    FractionalThreshold(
                        when (direction) {
                            DismissDirection.EndToStart -> 0.2f
                            DismissDirection.StartToEnd -> 0.2f
                            else -> 0.05f
                        }
                    )
                },
//                directions = setOf(DismissDirection.EndToStart)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipBackground(state: DismissState) {

    val scale by animateFloatAsState(
        if (state.targetValue == DismissValue.Default) 0.75f else 1f
    )
    val colour = when (state.dismissDirection) {
        DismissDirection.EndToStart -> Teal200
        DismissDirection.StartToEnd -> Purple200
        null -> Color.Unspecified
    }
    Row(
        modifier = Modifier.clip(RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.Center
    ){
        Spacer(modifier = Modifier
            .fillMaxWidth(0.05f)
            .padding())
        Row(
            modifier = Modifier
                .fillMaxWidth(0.948f)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxHeight(0.85f)
                .background(color = colour),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (state.dismissDirection) {
                DismissDirection.StartToEnd -> {
                    Spacer(modifier = Modifier.fillMaxWidth(0.03f))
                    Icon(Icons.Filled.AddBox, contentDescription = null, modifier = Modifier.scale(scale))
                }
                DismissDirection.EndToStart -> {
                    Spacer(modifier = Modifier.fillMaxWidth(0.9f))
                    Icon(Icons.Filled.Delete, contentDescription = null, modifier = Modifier.scale(scale))
                }
            }
        }
    }
}
