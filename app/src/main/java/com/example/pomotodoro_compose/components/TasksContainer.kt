package com.example.pomotodoro_compose.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.data.TasksData

@Composable
fun TasksContainer(list: MutableList<TasksData>, type: String) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight(0.92f)
    ) {
        items(list) { item ->
            TaskItem(item, type)
        }
    }
}