package com.example.pomotodoro_compose.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.pages.TimeLine
import com.example.pomotodoro_compose.pages.Today

@Composable
fun SubNavigation(navController: NavController, type: String, list: MutableList<TasksData>) {
    NavHost(navController = navController as NavHostController, startDestination = "today") {
        composable("timeline") { TimeLine(type, list) }
        composable("today") { Today(type, list) }
    }
}