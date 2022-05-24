package com.example.pomotodoro_compose.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomotodoro_compose.data.GroupTagListData
import com.example.pomotodoro_compose.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel


@Composable
fun GroupTagList(
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel,
    groupTagViewModel: GroupTagViewModel
){

    val list: MutableList<GroupTagListData> = groupTagViewModel.groupTagList
    LazyRow{
//        item { GroupTagItem(name = "All", colour = Color.Blue) }
        items(list){ item ->
            GroupTagItem(name = item.groupTagName, colour = item.colour, tasksViewModel = tasksViewModel)
        }
    }
}