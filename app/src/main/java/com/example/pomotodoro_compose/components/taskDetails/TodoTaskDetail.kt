package com.example.pomotodoro_compose.components.taskDetails

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoTaskDetail(
    tasksViewModel: TasksViewModel,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
    type: String
) {
    val data: TasksData = tasksViewModel.getItem()
    var pomoNum by remember { mutableStateOf(data.pomoTimes) }
    var priorityFlag by remember { mutableStateOf(data.priority) }
    var checked by remember { mutableStateOf(data.isChecked) }
    var reminder by remember { mutableStateOf(data.isRemindered) }
//    var setTaskTime by remember { mutableStateOf(data.setTaskTime) }
    var text by remember { mutableStateOf(data.title) }
    var editFlag by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    // Fetching local context
    val mContext = LocalContext.current
    // Declaring and initializing a calendar
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]
    // Value for storing time as a string
    var mTime by remember { mutableStateOf(data.setTaskTime) }
    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            mTime = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )

    LaunchedEffect(bottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
        focusManager.clearFocus()
//        Log.i("/debug", "hide")
    }

    LaunchedEffect(mTime != "Set Task Time") {
        tasksViewModel.upgradeTask(
            type = type,
            id = data.id,
            name = "setTaskTime",
            value = mTime
        )
        Log.i("/check", mTime)
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
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
        ) {
            for (it in listOf<Int>(1, 2, 3, 4, 5)) {
                IconButton(
                    onClick = {
                        pomoNum = it
                        tasksViewModel.upgradeTask(
                            type = type,
                            id = data.id,
                            name = "pomoTimes",
                            value = it
                        )
                    },
                    modifier = Modifier.width(35.dp)
                ) {
                    if (it <= pomoNum)
                        Icon(
                            Icons.Filled.Timer,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
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
            Switch(
                checked = priorityFlag,
                onCheckedChange = {
                    priorityFlag = it
                    tasksViewModel.upgradeTask(
                        type = type,
                        id = data.id,
                        name = "priority",
                        value = it
                    )
                },
                colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colors.primary)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            Text("Checked", fontWeight = Bold)
            Switch(checked = checked, onCheckedChange = {
                checked = it
                tasksViewModel.upgradeTask(
                    type = type,
                    id = data.id,
                    name = "isChecked",
                    value = it
                )
            })
        }
        Row(
            modifier = Modifier.padding(top = 5.dp, bottom = 15.dp),
        ) {
            IconButton(onClick = {
                reminder = !reminder
                tasksViewModel.upgradeTask(
                    type = type,
                    id = data.id,
                    name = "isRemindered",
                    value = reminder
                )
            }) {
                if (reminder)
                    Icon(
                        Icons.Filled.NotificationsActive,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                else
                    Icon(Icons.Filled.NotificationsActive, contentDescription = null)
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            OutlinedButton(
                onClick = { mTimePickerDialog.show() },
                border = BorderStroke(2.dp, color = Color.LightGray),
            ) {
                Text(text = mTime, color = Color.LightGray)
            }
        }

    }

}
