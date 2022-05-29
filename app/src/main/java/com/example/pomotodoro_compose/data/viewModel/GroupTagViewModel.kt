package com.example.pomotodoro_compose.data.viewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pomotodoro_compose.data.database.GroupTagDatabase
import com.example.pomotodoro_compose.data.entity.GroupTagData
import com.example.pomotodoro_compose.data.getGroupTagList
import com.example.pomotodoro_compose.data.repository.GroupTagRepository
import java.time.LocalDateTime

class GroupTagViewModel(application: Application): ViewModel() {
    private val _groupTagList: LiveData<List<GroupTagData>>
    private var repository: GroupTagRepository

    init {
        val groupTagDao = GroupTagDatabase.getInstance(application).groupTagDao()
        repository = GroupTagRepository(groupTagDao)
        _groupTagList = repository.allGroupTags
    }

    val groupTagList: List<GroupTagData>?
        get() = _groupTagList.value

//    private var _changeFlag by mutableStateOf(false)
//    val changeFlag:Boolean
//        get() = _changeFlag
//
//    fun restoreChangeFlag(){
//        _changeFlag = false
//    }

    fun addGroupTag(name: String, colour: Color){
        val item = GroupTagData(groupTagName = name, colour = colour, tagId = LocalDateTime.now().toString())
        _groupTagList.add(item)
    }

    fun getMatchedGroupTagData(list: List<String>): List<GroupTagData>{
        // list is id only
        val result : MutableList<GroupTagData> = mutableListOf()
        _groupTagList.value?.forEachIndexed { _, data ->
            for (item in list){
                if(item == data.tagId && data.tagId != "tag"){
                    result.add(data)
                }
            }
        }
        return result.toList()
    }

    fun deleteGroupTag(tagId : String){
        _groupTagList.removeAll{ it.tagId == tagId}
    }

    fun editGroupTag(tagId: String, value: String){
        _groupTagList.find { it.tagId == tagId }?.groupTagName = value
//        _changeFlag = true
    }
}