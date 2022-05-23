package com.example.pomotodoro_compose.components.taskDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.ui.theme.Purple500
import com.example.pomotodoro_compose.ui.theme.Purple700
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoTaskDetail(
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState
) {
    val data: TasksData = tasksViewModel.getItem()

    var text by remember { mutableStateOf(data.title) }
    var editFlag by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { editFlag = !editFlag }, modifier = Modifier.height(55.dp)) {
                Icon(Icons.Filled.Edit, contentDescription = null)
            }
            if (editFlag) {
                OutlinedTextField(
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    value = text,
                    onValueChange = { text = it },
                    enabled = editFlag,
                    singleLine = true,
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(0.7f)
                        .padding(horizontal = 5.dp)
                )
            } else {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.height(55.dp)) {
                Icon(Icons.Filled.Delete, contentDescription = null, tint = Purple500)
            }
        }
        Row(
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
        ) {
            for (it in listOf<Int>(1, 2, 3, 4, 5)) {
                IconButton(onClick = { }, modifier = Modifier.width(35.dp)) {
                    if (it <= data.pomoTimes)
                        Icon(Icons.Filled.Timer, contentDescription = null, tint = Purple500)
                    else
                        Icon(Icons.Filled.Timer, contentDescription = null)
                }
            }
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 30.dp)) {
                Icon(Icons.Filled.PlayCircle, contentDescription = null)
            }
        }
        //        if(bottomSheetState.currentValue == ModalBottomSheetValue.Expanded)
        Row(
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Priority", fontWeight = Bold)
            Switch(checked = data.priority, onCheckedChange = {}, colors = SwitchDefaults.colors(checkedThumbColor = Purple500))
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            Text("Repeat", fontWeight = Bold)
            Switch(checked = data.repeat, onCheckedChange = {})
        }
        Row(
            modifier = Modifier.padding(top = 5.dp, bottom = 15.dp),
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Filled.NotificationsActive,
                    contentDescription = null,
                    tint = Purple700
                )
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            OutlinedButton(
                onClick = { /*TODO*/ },
                border = BorderStroke(2.dp, color = Color.LightGray),
            ) {
                Text(text = "Set Task Time", color = Color.LightGray)
            }
        }

    }

}