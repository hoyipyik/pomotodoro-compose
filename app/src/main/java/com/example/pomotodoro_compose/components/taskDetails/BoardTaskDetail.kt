package com.example.pomotodoro_compose.components.taskDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.data.entity.TasksData
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoardTaskDetail(
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    type: String,
    stateViewModel: StateViewModel,
    groupTagViewModel: GroupTagViewModel
) {
    var data: TasksData = tasksViewModel.getItem()
    var priorityFlag by remember { mutableStateOf(data.toToday) }
    var isChecked by remember { mutableStateOf(data.isChecked) }
    var text by remember { mutableStateOf(data.title) }
    var editFlag by remember { mutableStateOf(false) }
    var tagData = groupTagViewModel.getMatchedGroupTagData(data.groupTag)

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()
    var deleteFlag by remember { mutableStateOf(false) }
    var selectorFlag by remember { mutableStateOf(false) }

    LaunchedEffect(tasksViewModel.getItem(), deleteFlag) {
        data = tasksViewModel.getItem()
        text = data.title
        editFlag = false
        priorityFlag = data.toToday
        tagData = groupTagViewModel.getMatchedGroupTagData(data.groupTag)
        deleteFlag = false
        isChecked = data.isChecked
        selectorFlag = false
    }

    LaunchedEffect(tasksViewModel.changeFlag){
        isChecked = data.isChecked
        tasksViewModel.restoreChangeTagListFlag()
    }

    LaunchedEffect(bottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
        focusManager.clearFocus()
        editFlag = false
        deleteFlag = false
        selectorFlag = false
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp)
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
            Text("Checked", fontWeight = FontWeight.Bold)
            Switch(checked = isChecked, onCheckedChange = {
                isChecked = it
                if(it)
                    tasksViewModel.upgradeTask(type = type, name = "finishTime", id = data.id, value = LocalDateTime.now().toString())
                else
                    tasksViewModel.upgradeTask(type = type, name = "finishTime", id = data.id, value = null)
                tasksViewModel.upgradeTask(
                    type = type,
                    id = data.id,
                    name = "isChecked",
                    value = it
                )
            })
        }
        Row(
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = if(selectorFlag) "Selector" else "Tags", fontWeight = FontWeight.Bold, modifier = Modifier.width(66.dp))
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = if(selectorFlag)"  Tag to add  " else "Tap to delete")
            Spacer(modifier = Modifier.padding(5.dp))
//            OutlinedButton(onClick = { /*TODO*/ }) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text(text = "Add", modifier = Modifier.padding(end = 3.dp, start = 8.dp))
//                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
//                }
//            }
            Spacer(modifier = Modifier.fillMaxWidth(0.06f))
            OutlinedButton(onClick = { selectorFlag = !selectorFlag }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (selectorFlag)
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                    else
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            tint = Color.LightGray
                        )
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
                if (selectorFlag) {
                    val list = groupTagViewModel.groupTagList.toMutableList()
                    list.removeAt(0)
                    items(list) { item ->
                        OutlinedButton(
                            onClick = {
                                tasksViewModel.upgradeGroupTag(
                                    type = type,
                                    id = data.id,
                                    value = item.tagId,
                                    name = "add"
                                )
                                deleteFlag = true
                            },
                            modifier = Modifier.padding(end = 5.dp),
                            border = BorderStroke(2.dp, color = Color(item.colour)),
                        ) {
                            Text(
                                text = item.groupTagName,
                                color = Color(item.colour),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(tagData) { item ->
                        OutlinedButton(
                            onClick = {
                                tasksViewModel.upgradeGroupTag(
                                    type = type,
                                    id = data.id,
                                    value = item.tagId,
                                    name = "remove"
                                )
                                deleteFlag = true
                            },
                            modifier = Modifier.padding(end = 5.dp),
                            border = BorderStroke(2.dp, color = Color(item.colour)),
                        ) {
                            Text(
                                text = item.groupTagName,
                                color = Color(item.colour),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

    }
}
