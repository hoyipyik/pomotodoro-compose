package com.example.pomotodoro_compose.pages

import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Account(scope: CoroutineScope, state: ModalBottomSheetState, tasksViewModel: TasksViewModel,) {
    val context = LocalContext.current
    Button(onClick = { tasksViewModel.notificationJudger(context = context, LocalTime.now())}) {
        Text(text = "Nofity")
    }
}