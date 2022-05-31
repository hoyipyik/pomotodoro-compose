package com.example.pomotodoro_compose.data.viewModel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomotodoro_compose.data.api.AccountApi
import com.example.pomotodoro_compose.data.api.AccountApiService
import com.example.pomotodoro_compose.data.api.TasksApi
import com.example.pomotodoro_compose.data.api.TasksApiService
import com.example.pomotodoro_compose.data.entity.AccountData
import com.example.pomotodoro_compose.data.entity.ReplyMessage
import com.example.pomotodoro_compose.data.entity.TasksData
import com.example.pomotodoro_compose.data.getTasksList
import com.example.pomotodoro_compose.data.getTodoTasksList
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.time.LocalDateTime
import java.time.LocalTime

class TasksViewModel(application: Application) : ViewModel() {
//    private var _tasksList = getBoardTasksList(_holderList).toMutableStateList()
    private var _logFailFlag by mutableStateOf(false)
    val logFailFlag: Boolean
        get() = _logFailFlag

    private val api: TasksApiService = TasksApi.retrofitService
    private lateinit var data: MutableList<TasksData>
    private lateinit var res: Response<ResponseBody>

    private var _tasksList = mutableListOf<TasksData>().toMutableStateList()
    private var _todoTasksList = mutableListOf<TasksData>().toMutableStateList()

    private val accountApi: AccountApiService = AccountApi.retrofitService
    var accountId: String by mutableStateOf("")
    var doneBoardWorkNum: Int by mutableStateOf(0)
    var doneTodoWorkNum: Int by mutableStateOf(0)

    init {
        if (accountId != "") {
            getAllData(accountId)
        } else {
            _tasksList = mutableListOf<TasksData>().toMutableStateList()
            _todoTasksList = mutableListOf<TasksData>().toMutableStateList()
        }
    }

    private fun changeLogFailFlag(flag: Boolean){
        _logFailFlag = flag
    }
    fun deleteAccount(id: String){
        viewModelScope.launch {
            accountApi.deleteAccount(AccountData(accountId = id))
        }
    }

    fun clearAllTasks(id: String){
        _tasksList = mutableListOf<TasksData>().toMutableStateList()
        _todoTasksList = mutableListOf<TasksData>().toMutableStateList()
        viewModelScope.launch {
            accountApi.clearAllData(AccountData(accountId = id))
        }
    }
    private fun getAllData(id: String) {
        viewModelScope.launch {
            try {
                data = api.getFullTasksData(AccountData(accountId = id, password = ""))
                data.forEachIndexed { _, item ->
                    _tasksList.add(item)
                    if (item.toToday) {
                        _todoTasksList.add(item)
                    }
                }
                Log.i("/fetchingdata", data.toString())

            } catch (e: Exception) {
                Log.e("/fetchingdata_error", e.toString())
            }
        }
    }

    private fun addData(task: TasksData) {
        viewModelScope.launch {
            try {
                task.accountId = accountId
                res = api.addTask(task)
                Log.i("/fetchaddData", res.toString())
            } catch (e: Exception) {
                Log.e("/fetchaddData_error", e.toString())
            }
        }
    }

    private fun deleteData(id: String) {
        viewModelScope.launch {
            try {
                res = api.deleteTask(AccountData(accountId = accountId, id = id, password = ""))
                Log.i("/fetchdeleteData", res.toString())
            } catch (e: Exception) {
                Log.e("/fetchdeleteData_error", e.toString())
            }
        }
    }

    private fun updateData(task: TasksData, id: String) {
        viewModelScope.launch {
            try {
                task.accountId = accountId
                res = api.upgradeTask(task)
                Log.i("/fetchupdateData", res.toString())
            } catch (e: Exception) {
                Log.e("/fetchupdateData_error", e.toString())
            }
        }
    }

    fun logout(){
        accountId = ""
    }

    val todoTasksList: MutableList<TasksData>
        get() = _todoTasksList.toMutableStateList()

    val boardTasksList: MutableList<TasksData>
        get() = _tasksList.toMutableStateList()

    private var _selectedId: String by mutableStateOf("")
    val selectedId
        get() = _selectedId

    private var _changeTagListFlag by mutableStateOf(false)
    val changeTagListFlag
        get() = _changeTagListFlag

    private var _changeFlag by mutableStateOf(false)
    val changeFlag
        get() = _changeFlag

    fun restoreChangeFlag() {
        _changeFlag = false
    }

    fun restoreChangeTagListFlag() {
        _changeTagListFlag = false
    }

    fun deleteTask(type: String, id: String) {
        _tasksList.removeAll { it.id == id }
        deleteData(id)
        if (type == "todo")
            _todoTasksList.removeAll { it.id == id }
        else
            _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
    }

    fun upgradeGroupTag(type: String, id: String, value: String, name: String) {
        when (name) {
            "remove" -> {
                val item = _tasksList.find { it.id == id }
                item?.groupTag!!.remove(value)
                updateData(item, id)
                _tasksList.find { it.id == id }?.groupTag = item.groupTag
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "add" -> {
                val item = _tasksList.find { it.id == id }
                if (!item?.groupTag!!.contains(value)) {
                    item.groupTag.add(value)
                    updateData(item, id)
                    _tasksList.find { it.id == id }?.groupTag = item.groupTag
                    _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                }
            }
        }
        _changeTagListFlag = true
    }

    fun upgradeTask(type: String, id: String, name: String, value: Any?) {
        val item = _tasksList.find { it.id == id }
        when (name) {
            "toToday" -> {
                item!!.toToday = value as Boolean
                updateData(item, id)
                _tasksList.find { it.id == id }?.let { it.toToday = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "title" -> {
                item!!.title = value as String
                updateData(item, id)
                _tasksList.find { it.id == id }?.let { it.title = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "isChecked" -> {
                if(value as Boolean) {
                    item!!.finishTime = LocalDateTime.now().toString()
                }else{
                    item!!.finishTime = null
                }
                item.isChecked = value as Boolean
                updateData(item, id)
                _tasksList.find { it.id == id }?.let { it.isChecked = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "priority" -> {
                item!!.priority = value as Boolean
                updateData(item, id)
                _tasksList.find { it.id == id }?.let { it.priority = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "repeat" -> {
                item!!.repeat = value as Boolean
                updateData(item, id)
                _tasksList.find { it.id == id }?.let { it.repeat = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "setTaskTime" -> {
                item!!.setTaskTime = value as String
                updateData(item, id)
                _tasksList.find { it.id == id }?.let { it.setTaskTime = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "pomoTimes" -> {
                item!!.pomoTimes = value as Int
                updateData(item, id)
                _tasksList.find { it.id == id }?.let { it.pomoTimes = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "isRemindered" -> {
                item!!.isRemindered = value as Boolean
                updateData(item, id)
                _tasksList.find { it.id == id }?.let { it.isRemindered = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
        }
        _changeFlag = true
    }

    fun addTask(
        type: String,
        text: String,
        pomoNum: Int = 0,
        groupTag: MutableList<String> = mutableListOf("tag")
    ) {
        val id: String = LocalDateTime.now().toString()
        val toToday: Boolean = type == "todo"
        val title: String = text
        val pomoTimes: Int = pomoNum
        val groupTags: MutableList<String> = groupTag
        val item = TasksData(
            id = id,
            toToday = toToday,
            title = title,
            pomoTimes = pomoTimes,
            groupTag = groupTags
        )
        _tasksList.add(item)
        addData(item)
        if (type == "todo") {
            _todoTasksList.add(item)
        }
    }

    fun getItem(id: String = _selectedId): TasksData {
        val result = _tasksList.find { it.id == id }
        return result ?: TasksData()
    }

    fun sendId(id: String) {
        _selectedId = id
    }

    private var _selectedGroupTag by mutableStateOf("tag")
    val selectedGroupTag
        get() = _selectedGroupTag

    fun upgradeSelectedGroupTag(id: String) {
        _selectedGroupTag = id
    }

    private fun changeAccountId(id: String){
        accountId = id
        Log.i("/fetch_test_accountId", accountId)
    }

    fun sendLogInfo(id: String, password: String) {
        viewModelScope.launch {
            try {
                val msg: ReplyMessage =
                    accountApi.sendAccount(AccountData(id, password))
                Log.i("/fetch_account_log_reply", msg.toString())
                if(msg.code == 200){
                    changeAccountId(id)
                    getAllData(id)
                }
            } catch (e: Exception) {
                Log.e("/fetch_account_log_reply_error", e.toString())
                changeLogFailFlag(true)
            }
        }
    }


    private fun createNotificationChannel(context: Context, head: String) {
        val name = "Task notification"
        val descriptionText = "Task: $head. \n Time to work :)"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("Channel_id", name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun notificationJudger(context: Context, currentTime: LocalTime) {
        createNotificationChannel(context = context, head = "Hi")
//        _tasksList.forEachIndexed{ _, data ->
//            if(data.isRemindered){
//                val newFormatedTime: LocalTime = LocalTime.parse(data.setTaskTime, DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
//                val timeMinutes = newFormatedTime.hour * 60 + newFormatedTime.minute
//                val currentMinutes = currentTime.hour * 60 + currentTime.minute
//                Log.i("/notitester", newFormatedTime.toString() + data.setTaskTime)
//                if(timeMinutes -5 >= currentMinutes){
//                    createNotificationChannel(context = context, title = data.title)
//                }
//            }
//        }
    }
}

