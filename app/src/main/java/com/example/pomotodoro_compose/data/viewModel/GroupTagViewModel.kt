package com.example.pomotodoro_compose.data.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomotodoro_compose.data.api.AccountApi
import com.example.pomotodoro_compose.data.api.AccountApiService
import com.example.pomotodoro_compose.data.api.GroupTagApi
import com.example.pomotodoro_compose.data.api.GroupTagApiService
import com.example.pomotodoro_compose.data.entity.AccountData
import com.example.pomotodoro_compose.data.entity.GroupTagListData
import com.example.pomotodoro_compose.data.entity.ReplyMessage
import com.example.pomotodoro_compose.data.entity.TasksData
import com.example.pomotodoro_compose.data.getGroupTagList
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class GroupTagViewModel: ViewModel() {

    private var api: GroupTagApiService = GroupTagApi.retrofitService
    private lateinit var data: MutableList<GroupTagListData>
    private lateinit var res: Response<ResponseBody>
    private var _groupTagList = mutableListOf<GroupTagListData>().toMutableStateList()
//    private val _groupTagList = getGroupTagList().toMutableStateList()
    private val accountApi: AccountApiService = AccountApi.retrofitService
    private var accountId: String by mutableStateOf("")

    fun logout(){
        accountId = ""
        _groupTagList = getGroupTagList().toMutableStateList()
    }

    fun clearAllData(){
        _groupTagList = getGroupTagList().toMutableStateList()
    }

    init {
        if(accountId != ""){
            getAllGroupTagData(accountId)
        }else{
            _groupTagList = getGroupTagList().toMutableStateList()
        }
    }

    fun refreshGroupTagData(){
        if(accountId != ""){
            getAllGroupTagData(accountId)
        }else{
            _groupTagList = getGroupTagList().toMutableStateList()
        }
    }

    fun sendLogInfo(id: String, password: String) {
        viewModelScope.launch {
            try {
                val msg: ReplyMessage =
                    accountApi.sendAccount(AccountData(id, password))
                Log.i("/fetch_account2_log_reply", msg.toString())
                if(msg.code == 200){
                    updataAccountId(id)
                    getAllGroupTagData(id)
                }
            } catch (e: Exception) {
                Log.e("/fetch_account2_log_reply_error", e.toString())
            }
        }
    }

    private fun updataAccountId(id: String){
        accountId = id
    }
    private fun getAllGroupTagData(id :String){
        viewModelScope.launch {
            try {
                data = api.getGroupTag(AccountData(accountId = id))
                data.forEachIndexed { _, item ->
                    _groupTagList.add(item)
                }
                Log.i("/fetch_group_tag_list", data.toString())
            } catch (e: Exception){
                Log.e("/fetch_group_tag_list_err", e.toString())
            }
        }
    }
    private fun addGroupTagData(item: GroupTagListData) {
        viewModelScope.launch {
            try {
                item.accountId = accountId
                res = api.addGroupTag(item)
                Log.i("/fetch_add_group_tag", res.toString())
            } catch (e: Exception) {
                Log.e("/fetch_add_group_tag_err", e.toString())
            }
        }
    }
    private fun updateGroupTagData(item: GroupTagListData) {
        viewModelScope.launch {
            try {
                item.accountId = accountId
                res = api.updateGroupTag(item)
                Log.i("/fetch_update_group_tag", res.toString())
            } catch (e: Exception) {
                Log.e("/fetch_update_group_tag_err", e.toString())
            }
        }
    }

    private fun deleteGroupTagData(item: String) {
        viewModelScope.launch {
            try {
                res = api.deleteGroupTag(AccountData(id = item, accountId = accountId))
                Log.i("/fetch_delete_group_tag", res.toString())
            } catch (e: Exception) {
                Log.e("/fetch_delete_group_tag_err", e.toString())
            }
        }
    }

    val groupTagList:MutableList<GroupTagListData>
        get() = _groupTagList.toMutableStateList()

    private var _changeFlag by mutableStateOf(false)
    val changeFlag:Boolean
        get() = _changeFlag

    fun restoreChangeFlag(){
        _changeFlag = false
    }

    fun addGroupTag(name: String, colour: Color){
        val item = GroupTagListData(groupTagName = name, colour = colour.toArgb())
        _groupTagList.add(item)
        addGroupTagData(item)
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

    private var _groupTagDeleteFlag by mutableStateOf(false)
    val groupTagDeleteFlag:Boolean
        get() = _groupTagDeleteFlag

    fun restoreGroupTagDeleteFlag(){
        _groupTagDeleteFlag = false
    }

    fun deleteGroupTag(tagId : String){
        _groupTagList.removeAll{ it.tagId == tagId}
        _groupTagDeleteFlag = true
        deleteGroupTagData(tagId)
    }

    fun editGroupTag(tagId: String, value: String){
        _groupTagList.find { it.tagId == tagId }?.groupTagName = value
        _changeFlag = true
        val item: GroupTagListData = _groupTagList.find { it.tagId == tagId }!!
        item.groupTagName = value
        updateGroupTagData(item)
    }
}