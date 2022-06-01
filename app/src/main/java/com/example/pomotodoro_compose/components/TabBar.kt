package com.example.pomotodoro_compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel

@Composable
fun TabBar(
    navController: NavHostController,
    tasksViewModel: TasksViewModel,
    stateViewModel: StateViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        TextButton(
//            enabled = false,
            onClick = {
                tasksViewModel.timelineCalcalation()
                navController.navigate("timeline") {
                    currentRoute?.let { popUpTo(it) { inclusive = true } }
                }
                stateViewModel.subNavRoute = "timeline"
            }) {
            Text(text = "Timeline")
        }
        TextButton(
            onClick = {
                navController.navigate("today") {
                    currentRoute?.let { popUpTo(it) { inclusive = true } }
                }
                stateViewModel.subNavRoute = "today"
            }) {
            Text(text = "Daily Tasks")
        }
    }
}