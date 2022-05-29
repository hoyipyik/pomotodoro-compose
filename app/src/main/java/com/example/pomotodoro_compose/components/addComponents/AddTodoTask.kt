package com.example.pomotodoro_compose.components.addComponents

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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    val focusRequester = FocusRequester()
    // need optimism
//    LaunchedEffect(bottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
//        // open keyboard
//        focusRequester.requestFocus()
//        Log.i("/debug", "show")
//    }

    LaunchedEffect(bottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
        focusManager.clearFocus()
        pomoNum = 0
        text = ""
//        Log.i("/debug", "hide")
    }

    Column(
        modifier = modifier.padding(top = 15.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.74f)
                    .padding( end = 9.dp)
                    .height(55.dp)
                    .focusRequester(focusRequester),
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
                onClick = {
                    if(text != "") {
                        tasksViewModel.addTask(type = type, text = text, pomoNum = pomoNum)
                        scope.launch { bottomSheetState.hide() }
                        pomoNum = 0
                        text = ""
                    }
                },
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
                        Icon(Icons.Filled.Timer, contentDescription = null, tint = MaterialTheme.colors.primary)
                    else
                        Icon(Icons.Filled.Timer, contentDescription = null)
                }
            }

        }
    }
}