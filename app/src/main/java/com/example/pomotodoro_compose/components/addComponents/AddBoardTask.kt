package com.example.pomotodoro_compose.components.addComponents

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddBoardTask(
    type: String,
    modifier: Modifier,
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    groupTagViewModel: GroupTagViewModel
) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    // need optimism
    LaunchedEffect(!bottomSheetState.isVisible) {
        focusManager.clearFocus()
//        Log.i("/debug", "hide")
    }
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
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
                onClick = {
                    if(text != "") {
                        tasksViewModel.addTask(type = type, text = text)
                        scope.launch { bottomSheetState.hide() }
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
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Tags", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.fillMaxWidth(0.12f))
            OutlinedButton(onClick = { /*TODO*/ }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Add", modifier = Modifier.padding(end = 3.dp, start = 8.dp))
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.06f))
            OutlinedButton(onClick = { /*TODO*/ }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 15.dp)
                .fillMaxWidth(0.74f)
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.onSecondary),
        ) {
            LazyRow(Modifier.padding(horizontal = 6.dp)) {
//                items(tagData) { item ->
//                    OutlinedButton(
//                        onClick = {},
//                        modifier = Modifier.padding(end = 5.dp),
//                        border = BorderStroke(2.dp, color = item.colour),
////        colors = ButtonDefaults.buttonColors(backgroundColor = colour)
//                    ) {
//                        Text(text = item.groupTagName, color = item.colour, textAlign = TextAlign.Center)
//                        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.height(1.dp).width(1.dp)){}
////                                Icon(imageVector = Icons.Filled.CancelPresentation, contentDescription = null, modifier = Modifier.height(40.dp).width(35.dp))
//                    }
//                }
            }
        }
    }
}