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

@Composable
fun TabBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = {
                navController.navigate("timeline") {
                    currentRoute?.let { popUpTo(it) { inclusive = true } }
                }
            }) {
            Text(text = "Timeline")
        }
        TextButton(
            onClick = {
                navController.navigate("today") {
                    currentRoute?.let { popUpTo(it) { inclusive = true } }
                }
            }) {
            Text(text = "Daily Tasks")
        }
    }
}