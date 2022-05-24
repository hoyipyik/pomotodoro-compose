package com.example.pomotodoro_compose.components.grouptag

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.viewModel.TasksViewModel

@Composable
fun GroupTagItem(name: String, colour: Color, tasksViewModel: TasksViewModel, id: String) {
    OutlinedButton(
        onClick = {
                  tasksViewModel.upgradeSelectedGroupTag(id = id)
        },
        modifier = Modifier.padding(end = 5.dp),
        border = BorderStroke(2.dp, color = colour),
//        colors = ButtonDefaults.buttonColors(backgroundColor = colour)
    ) {
        Text(text = name, color = colour)
    }
}
