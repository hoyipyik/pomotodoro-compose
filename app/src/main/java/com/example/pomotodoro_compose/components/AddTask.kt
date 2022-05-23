package com.example.pomotodoro_compose.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.ui.theme.Bluelight
import com.example.pomotodoro_compose.ui.theme.Purple500
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTask(
    type: String,
    modifier: Modifier,
    tasksViewModel: TasksViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope
) {
    if (type == "todo") {
        AddTodoTask(
            type = type,
            modifier = modifier,
            tasksViewModel = tasksViewModel,
            scope = scope,
            bottomSheetState = bottomSheetState
        )
    } else {
        AddBoardTask(
            type = type,
            modifier = modifier,
            tasksViewModel = tasksViewModel,
            scope = scope,
            bottomSheetState = bottomSheetState
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTodoTask(
    type: String,
    modifier: Modifier,
    tasksViewModel: TasksViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope
) {
    var pomoNum by remember { mutableStateOf(0) }
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    // need optimism
    if (!bottomSheetState.isVisible) {
        Log.i("/debug", "worked")
        focusManager.clearFocus()
    }
    Column(modifier = modifier.padding(top = 15.dp, start = 15.dp, end = 5.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
//            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(start = 10.dp, end = 9.dp)
                    .height(55.dp),
                value = text,
                placeholder = { Text(text = "Add a task...") },
                onValueChange = { text = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
            Button(
                onClick = { scope.launch { bottomSheetState.hide() } },
                modifier = Modifier
                    .width(45.dp)
                    .height(45.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 1.dp, bottom = 15.dp, start = 17.dp),
        ) {
            Text(text = "Pomo Times", modifier = Modifier.padding(end = 10.dp))
            for (it in listOf<Int>(1, 2, 3, 4, 5)) {
                IconButton(onClick = { pomoNum = it }, modifier = Modifier.width(35.dp)) {
                    if (it <= pomoNum)
                        Icon(Icons.Filled.Timer, contentDescription = null, tint = Purple500)
                    else
                        Icon(Icons.Filled.Timer, contentDescription = null)
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddBoardTask(
    type: String,
    modifier: Modifier,
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState
) {
    Text(
        text = "BOARD",
        modifier = modifier
    )
}