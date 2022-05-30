package com.example.pomotodoro_compose.components.grouptag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.data.GroupTagListData
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditGroupTagItem(
    item: GroupTagListData,
    groupTagViewModel: GroupTagViewModel,
    bottomSheetState: ModalBottomSheetState,
    tasksViewModel: TasksViewModel
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    var editFlag by remember{ mutableStateOf(false) }
    var text by remember{ mutableStateOf(item.groupTagName) }

    LaunchedEffect(bottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
        focusManager.clearFocus()
        editFlag = false
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 6.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.onSecondary),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { editFlag = !editFlag }, modifier = Modifier.padding(start = 8.dp)) {
                Box(
                    Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(item.colour)
                )
            }
            if (editFlag) {
                OutlinedTextField(
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    value = text,
                    onValueChange = {
                        text = it
                        groupTagViewModel.editGroupTag(item.tagId, it)
                    },
                    enabled = editFlag,
                    singleLine = true,
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(0.7f)
                        .padding(horizontal = 5.dp)
                        .focusRequester(focusRequester)
                )
            }
            else
            Text(item.groupTagName, modifier = Modifier.fillMaxWidth(0.7f), textAlign = TextAlign.Center)
            IconButton(onClick = {
                groupTagViewModel.deleteGroupTag(item.tagId)
                if(item.tagId == tasksViewModel.selectedGroupTag){
                    tasksViewModel.upgradeSelectedGroupTag("tag")
                }
            }) {
                Icon(Icons.Filled.Delete, contentDescription = null)
            }
        }
    }
}