package com.example.pomotodoro_compose.pages

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import com.example.pomotodoro_compose.pages.account.AccountInfoPage
import com.example.pomotodoro_compose.pages.account.LoginPage
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Account(
    scope: CoroutineScope,
    state: ModalBottomSheetState,
    tasksViewModel: TasksViewModel,
    stateViewModel: StateViewModel,
    groupTagViewModel: GroupTagViewModel,) {
    val context = LocalContext.current
    if(tasksViewModel.accountId == "" ){
        LoginPage(tasksViewModel = tasksViewModel, stateViewModel = stateViewModel, groupTagViewModel = groupTagViewModel)
    }else{
        AccountInfoPage(tasksViewModel = tasksViewModel, stateViewModel = stateViewModel, groupTagViewModel = groupTagViewModel)
    }
}