package com.example.pomotodoro_compose.viewModel

import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.pomotodoro_compose.data.GroupTagListData
import com.example.pomotodoro_compose.data.getGroupTagList
import java.time.LocalDateTime

class GroupTagViewModel: ViewModel() {
    private val _groupTagList = getGroupTagList().toMutableStateList()
    val groupTagList:MutableList<GroupTagListData>
        get() = _groupTagList

    fun addGroupTag(name: String, colour: Color){
        val item = GroupTagListData(groupTagName = name, colour = colour, tagId = LocalDateTime.now().toString())
        _groupTagList.add(item)
    }
}