package com.example.pomotodoro_compose.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.data.getTasksList
import com.example.pomotodoro_compose.ui.theme.Bluelight
import com.example.pomotodoro_compose.viewModel.TasksViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TasksContainer(list: MutableList<TasksData>, type: String, tasksViewModel: TasksViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight(0.92f)
    ) {
        itemsIndexed(list) { index, item ->
            val state = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        list.remove(item)
                        Log.i("/remove", item.title)
                    }
                    if(it == DismissValue.DismissedToEnd){

                    }
                    true
                }
            )
            SwipeToDismiss(
                state = state,
                background = { SwipBackground(state = state) },
                dismissContent = {
                    TaskItem(item, type, tasksViewModel = tasksViewModel)
                },
                directions = setOf(DismissDirection.EndToStart)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipBackground(state: DismissState) {
    val colour = when (state.dismissDirection) {
        DismissDirection.EndToStart -> Color.Red
        DismissDirection.StartToEnd -> Bluelight
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
                    Icon(Icons.Filled.AddBox, contentDescription = null)
                }
                DismissDirection.EndToStart -> {
                    Spacer(modifier = Modifier.fillMaxWidth(0.9f))
                    Icon(Icons.Filled.Delete, contentDescription = null)
                }
            }
        }
    }
}
