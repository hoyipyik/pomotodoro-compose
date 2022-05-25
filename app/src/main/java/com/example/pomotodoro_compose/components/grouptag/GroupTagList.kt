package com.example.pomotodoro_compose.components.grouptag

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import com.example.pomotodoro_compose.components.grouptag.GroupTagItem
import com.example.pomotodoro_compose.data.GroupTagListData
import com.example.pomotodoro_compose.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel


@Composable
fun GroupTagList(
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel,
    groupTagViewModel: GroupTagViewModel
){

    var list: MutableList<GroupTagListData> = groupTagViewModel.groupTagList

    LaunchedEffect(groupTagViewModel.changeFlag){
        list = groupTagViewModel.groupTagList
        groupTagViewModel.restoreChangeFlag()
    }
    LazyRow{
//        item { GroupTagItem(name = "All", colour = Color.Blue) }
        items(list){ item ->
            GroupTagItem(name = item.groupTagName, id = item.tagId, colour = item.colour, tasksViewModel = tasksViewModel)
        }
    }
}