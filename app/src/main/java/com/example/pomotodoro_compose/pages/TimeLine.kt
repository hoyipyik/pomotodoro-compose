package com.example.pomotodoro_compose.pages

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.pomotodoro_compose.data.TasksData


@Composable
fun TimeLine(type: String, list: MutableList<TasksData>) {
    Text("This is timeline")
}