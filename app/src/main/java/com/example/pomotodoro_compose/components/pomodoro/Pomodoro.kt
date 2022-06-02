package com.example.pomotodoro_compose.components.pomodoro

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.WindowManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Pomodoro(
    tasksViewModel: TasksViewModel,
    stateViewModel: StateViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope
) {
    fun Context.findActivity(): Activity? {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        return null
    }
    val context = LocalContext.current
    LaunchedEffect(!bottomSheetState.isVisible){
        val window = context.findActivity()?.window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    DisposableEffect(Unit) {
        val window = context.findActivity()?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    val workSeconds = 25 * 60
    val restSeconds = 5 * 60

    val item = tasksViewModel.getItem()
    var pomoTimes by remember { mutableStateOf(item.pomoTimes) }
    var time by remember { mutableStateOf(workSeconds) }

    var isWork by remember { mutableStateOf(true) }
    var isFinished by remember { mutableStateOf(false) }

    var m = time / 60
    var s = time - m * 60

    LaunchedEffect(pomoTimes == 0) {
        if (pomoTimes == 0)
        isFinished = true
    }

    LaunchedEffect(key1 = time) {
        if(!isFinished){
            delay(1000L)
            time -= 1
            m = time / 60
            s = time - m * 60
            if (time == 0) {
                if (isWork) {
                    time = restSeconds
                } else {
                    time = workSeconds
                    if (pomoTimes - 1 >= 0)
                        pomoTimes -= 1
                    tasksViewModel.upgradeTask(
                        type = "todo",
                        id = item.id,
                        name = "pomoTimes",
                        value = pomoTimes
                    )
                }

                isWork = !isWork
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.14f))
            Box(
                contentAlignment = Alignment.Center
            ) {
                DrawCircles()
                Text(text = "$m : $s", fontSize = 40.sp, modifier = Modifier.padding(start = 0.dp))
            }
            Spacer(modifier = Modifier.padding(top = 37.dp))
            Text(if (isFinished) "You've Done" else "$pomoTimes Times Left")
            Spacer(modifier = Modifier.fillMaxHeight(0.23f))
            Button(
                onClick = {
                    if(!isFinished){
                        if (isWork) {
                            time = restSeconds
                        } else {
                            time = workSeconds
                            if (pomoTimes - 1 >= 0)
                                pomoTimes -= 1
                            tasksViewModel.upgradeTask(
                                type = "todo",
                                id = item.id,
                                name = "pomoTimes",
                                value = pomoTimes
                            )
                        }
                        m = time / 60
                        s = time - m * 60
                        isWork = !isWork
                    }
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onSecondary)
            ) {
                Text(text = "Next", fontSize = 17.sp)
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Button(
                onClick = {
                    time = if (isWork) workSeconds else restSeconds
                    m = time / 60
                    s = time - m * 60
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(45.dp),
            ) {
                Text(text = "Return", fontSize = 17.sp)
            }
        }
    }
}

