package com.example.pomotodoro_compose.container

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.components.grouptag.EditGroupTagItem
import com.example.pomotodoro_compose.viewModel.GroupTagViewModel

@Composable
fun EditGroupTagContainer(groupTagViewModel: GroupTagViewModel) {
    val list = groupTagViewModel.groupTagList
    LazyColumn(
        Modifier.fillMaxWidth().padding(vertical = 12.dp)
    ){
        items(list){ item ->
            EditGroupTagItem(item)
        }
    }
}