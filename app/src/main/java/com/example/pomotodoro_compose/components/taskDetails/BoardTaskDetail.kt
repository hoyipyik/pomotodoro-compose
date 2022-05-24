package com.example.pomotodoro_compose.components.taskDetails

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pomotodoro_compose.components.grouptag.GroupTagItem
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.viewModel.StateViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoardTaskDetail(
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    type: String,
    bottomSheetNavController: NavHostController,
    stateViewModel: StateViewModel,
    groupTagViewModel: GroupTagViewModel
) {
    val data: TasksData = tasksViewModel.getItem()
    var priorityFlag by remember { mutableStateOf(data.toToday) }
    var isRepeat by remember { mutableStateOf(data.repeat) }
//    var setTaskTime by remember { mutableStateOf(data.setTaskTime) }
    var text by remember { mutableStateOf(data.title) }
    var editFlag by remember { mutableStateOf(false) }

    val tagData = groupTagViewModel.getMatchedGroupTagData(data.groupTag)

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    LaunchedEffect(bottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
        focusManager.clearFocus()
//        Log.i("/debug", "hide")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (!editFlag) {
                        tasksViewModel.upgradeTask(
                            type = type,
                            id = data.id,
                            name = "title",
                            value = text
                        )
                    }
                    editFlag = !editFlag

                },
                modifier = Modifier.height(55.dp)
            ) {
                Icon(Icons.Filled.Edit, contentDescription = null)
            }
            if (editFlag) {
                OutlinedTextField(
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    value = text,
                    onValueChange = {
                        text = it
                        tasksViewModel.upgradeTask(
                            type = type,
                            id = data.id,
                            value = text,
                            name = "title"
                        )
                    },
                    enabled = editFlag,
                    singleLine = true,
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(0.7f)
                        .padding(horizontal = 5.dp)
                        .focusRequester(focusRequester)
                )
            } else {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }
            IconButton(
                onClick = {
                    tasksViewModel.deleteTask(type = type, id = data.id)
                    scope.launch { bottomSheetState.hide() }
                },
                modifier = Modifier.height(55.dp)
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        }

        Row(
            modifier = Modifier.padding(top = 0.dp, bottom = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Scheduled", fontWeight = FontWeight.Bold)
            Switch(
                checked = priorityFlag,
                onCheckedChange = {
                    priorityFlag = it
                    tasksViewModel.upgradeTask(
                        type = type,
                        id = data.id,
                        name = "toToday",
                        value = it
                    )
                },
                colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colors.primary)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            Text("Repeat", fontWeight = FontWeight.Bold)
            Switch(checked = isRepeat, onCheckedChange = {
                isRepeat = it
                tasksViewModel.upgradeTask(
                    type = type,
                    id = data.id,
                    name = "repeat",
                    value = it
                )
            })
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
                items(tagData) { item ->
                    OutlinedButton(
                        onClick = {},
                        modifier = Modifier.padding(end = 5.dp),
                        border = BorderStroke(2.dp, color = item.colour),
//        colors = ButtonDefaults.buttonColors(backgroundColor = colour)
                    ) {
                        Text(text = item.groupTagName, color = item.colour, textAlign = TextAlign.Center)
                        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.height(1.dp).width(1.dp)){}
//                                Icon(imageVector = Icons.Filled.CancelPresentation, contentDescription = null, modifier = Modifier.height(40.dp).width(35.dp))
                    }
                }
            }
        }

    }
}
