package com.example.pomotodoro_compose.router

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pomotodoro_compose.pages.Account
import com.example.pomotodoro_compose.pages.Board
import com.example.pomotodoro_compose.pages.Todo
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PageNavigation(
    scope: CoroutineScope,
    navController: NavController,
    bottomSheetState: ModalBottomSheetState
) {
    NavHost(navController = navController as NavHostController, startDestination = "board"){
        composable("board"){ Board(scope = scope, state = bottomSheetState) }
        composable("todo"){ Todo(scope = scope, state = bottomSheetState)}
        composable("account"){ Account()}
    }
}