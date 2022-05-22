package com.example.pomotodoro_compose.viewModel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.pomotodoro_compose.data.GroupTagListData
import com.example.pomotodoro_compose.data.getGroupTagList

class GroupTagViewModel: ViewModel() {
    private val _groupTagList = getGroupTagList().toMutableStateList()
    val groupTagList:MutableList<GroupTagListData>
        get() = _groupTagList
}