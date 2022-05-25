package com.example.pomotodoro_compose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    private var _changeFlag by mutableStateOf(false)
    val changeFlag:Boolean
        get() = _changeFlag

    fun restoreChangeFlag(){
        _changeFlag = false
    }

    fun addGroupTag(name: String, colour: Color){
        val item = GroupTagListData(groupTagName = name, colour = colour, tagId = LocalDateTime.now().toString())
        _groupTagList.add(item)
    }

    fun getMatchedGroupTagData(list: MutableList<String>): MutableList<GroupTagListData>{
        // list is id only
        val result : MutableList<GroupTagListData> = mutableListOf()
        _groupTagList.forEachIndexed { _, data ->
            for (item in list){
                if(item == data.tagId && data.tagId != "tag"){
                    result.add(data)
                }
            }
        }
        return result
    }

    fun deleteGroupTag(tagId : String){
        _groupTagList.removeAll{ it.tagId == tagId}
    }

    fun editGroupTag(tagId: String, value: String){
        _groupTagList.find { it.tagId == tagId }?.groupTagName = value
        _changeFlag = true
    }
}