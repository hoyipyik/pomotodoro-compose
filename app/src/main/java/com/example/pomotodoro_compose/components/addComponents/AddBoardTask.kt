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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.data.entity.GroupTagListData
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
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
    var showSelector by remember { mutableStateOf(false) }
    var selectorId = remember{mutableListOf<String>("tag")}
    var selectorData = remember{ mutableListOf<GroupTagListData>().toMutableStateList()}
    // need optimism
    LaunchedEffect(!bottomSheetState.isVisible) {
        focusManager.clearFocus()
        selectorId = mutableListOf("tag")
        text = ""
        showSelector = false
        selectorData = mutableListOf<GroupTagListData>().toMutableStateList()
//        Log.i("/debug", "hide")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 12.dp, bottom = 7.dp),
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
                    if (text != "") {
                        Log.i("/testid", selectorId.toString())
                        tasksViewModel.addTask(type = type, text = text, groupTag = selectorId)
                        scope.launch { bottomSheetState.hide() }
                        selectorId = mutableListOf("tag")
                        text = ""
                        showSelector = false
                        selectorData = mutableListOf<GroupTagListData>().toMutableStateList()
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
            modifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (showSelector) "Selector" else "   Tags   ",
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.12f))
            OutlinedButton(
                enabled = showSelector,
                onClick = {
                if (showSelector) {
                    showSelector = false
                }
            }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Add", modifier = Modifier.padding(end = 3.dp, start = 8.dp))
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.06f))
            OutlinedButton(onClick = { showSelector = !showSelector }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (showSelector)
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    else
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 13.dp, bottom = 35.dp)
                .fillMaxWidth(0.74f)
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.onSecondary),
        ) {
            LazyRow(Modifier.padding(horizontal = 6.dp)) {
                if (showSelector) {
                    val list = groupTagViewModel.groupTagList.toMutableList()
                    list.removeAt(0)
                    items(list) { item ->
                        OutlinedButton(
                            onClick = {
                                if(!selectorId.contains(item.tagId)) {
                                    selectorId.add(item.tagId)
                                    selectorData.add(item)
                                }
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
                    items(selectorData) { item ->
                        OutlinedButton(
                            onClick = {
                                selectorData.remove(item)
                                selectorId.remove(item.tagId)
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