package com.example.pomotodoro_compose.components.grouptag

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.pomotodoro_compose.data.entity.GroupTagData
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel


@Composable
fun GroupTagList(
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel,
    groupTagViewModel: GroupTagViewModel
){

    var list: List<GroupTagData> = groupTagViewModel.groupTagList

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