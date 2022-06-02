package com.example.pomotodoro_compose.data.viewModel

import android.app.Application
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
    private var _tasksDoneTotalNum: Int by mutableStateOf(0)
    val tasksDoneTotalNum: Int
        get() = _tasksDoneTotalNum
    private var _doneTodoWorkNum: Int by mutableStateOf(0)
    val doneTodoWorkNum: Int
        get() = _doneTodoWorkNum
    private var _unfinishedTodoWorkNum: Int by mutableStateOf(0)
    val unfinishedTodoWorkNum: Int
        get() = _unfinishedTodoWorkNum
    private var _overdueTaskNum: Int by mutableStateOf(0)
    val overdueTaskNum: Int
        get() = _overdueTaskNum
    private var _timelineList = mutableListOf<TasksData>().toMutableStateList()
    val timelineList
        get() = _timelineList

    init {
        if (accountId != "") {
            getAllData(accountId)
        } else {
            _tasksList = mutableListOf<TasksData>().toMutableStateList()
            _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
        }
    }

    fun timelineCalcalation() {
        calculateDoneTodoTaskNum()
        calculateOverdueTaskNum()
        timelineListGeneration()
    }

    private fun timelineListGeneration() {
        _timelineList = _todoTasksList.toMutableStateList()
        Log.i("/test_work", "timelineListGeneration: ${_timelineList.size}")
        _timelineList.sortBy { it.finishTime.toString() }
        _timelineList.sortBy { it.isChecked }
        _timelineList.sortBy { !it.isOverdue }
        for (item in _timelineList) {
            Log.i("/test_work_item", item.title.toString())
        }
    }

    fun calculateTotalDoneNum() {
        _tasksDoneTotalNum = 0
        for (item in _tasksList) {
            if (item.isChecked) {
                _tasksDoneTotalNum += 1
            }
        }
    }

    private fun calculateDoneTodoTaskNum() {
        _doneTodoWorkNum = 0
        _unfinishedTodoWorkNum = 0
        for (i in 0 until _todoTasksList.size) {
            if (_todoTasksList[i].isChecked) {
                _doneTodoWorkNum += 1
            } else {
                _unfinishedTodoWorkNum += 1
            }
        }
    }

    private fun calculateOverdueTaskNum() {
        _overdueTaskNum = 0
        _todoTasksList.forEachIndexed { index, data ->
            if (data.setTaskTime != "Set Task Time") {
//                val taskTime = LocalTime.parse(data.setTaskTime, DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                val nowH = LocalTime.now().hour
                val nowM = LocalTime.now().minute
                val taskTimeH = data.setTaskTime.split(":")[0].toInt()
                val taskTimeM = data.setTaskTime.split(":")[1].toInt()
                if (!data.isChecked) {
                    if (
                        (taskTimeH < nowH || (taskTimeH == nowH && taskTimeM <= nowM)) && data.isRemindered) {
                        _overdueTaskNum += 1
                        _todoTasksList[index].isOverdue = true
                        Log.i("/overdue", "overdue")
                    } else {
                        _todoTasksList[index].isOverdue = false
                    }
                }else{
                    val finishtimeH = LocalDateTime.parse(data.finishTime).hour
                    val finishtimeM = LocalDateTime.parse(data.finishTime).minute
                    if (
                        (finishtimeH > taskTimeH || (finishtimeH == taskTimeH && finishtimeM > taskTimeM)) && data.isRemindered) {
                        _overdueTaskNum += 1
                        _todoTasksList[index].isOverdue = true
                        Log.i("/overdue", "overdue")
                    } else {
                        _todoTasksList[index].isOverdue = false
                    }
                }
            }
        }

    }

    fun refreshData() {
        if (accountId != "") {
            getAllData(accountId)
        } else {
            _tasksList = mutableListOf<TasksData>().toMutableStateList()
            _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
        }
    }

    fun changeLogFailFlag(flag: Boolean) {
        _logFailFlag = flag
        Log.i("/fetch_fail_log_update", _logFailFlag.toString())
    }

    fun deleteAccount(id: String) {
        upgradeSelectedGroupTag("tag")
        viewModelScope.launch {
            accountApi.deleteAccount(AccountData(accountId = id))
        }
    }

    fun clearAllTasks(id: String) {
        _tasksList = mutableListOf<TasksData>().toMutableStateList()
        _todoTasksList = mutableListOf<TasksData>().toMutableStateList()
        upgradeSelectedGroupTag("tag")
        calculateTotalDoneNum()
        viewModelScope.launch {
            accountApi.clearAllData(AccountData(accountId = id))
        }
    }

    private fun getAllData(id: String) {
        if (accountId != "") {
            viewModelScope.launch {
                try {
                    data = api.getFullTasksData(AccountData(accountId = id, password = ""))
                    data.forEachIndexed { _, item ->
                        _tasksList.add(item)
                        if (item.toToday) {
                            _todoTasksList.add(item)
                        }
                    }
                    calculateTotalDoneNum()
                    Log.i("/fetchingdata", data.toString())

                } catch (e: Exception) {
                    Log.e("/fetchingdata_error", e.toString())
                }
            }
        }
    }

    private fun addData(task: TasksData) {
        if (accountId != "") {
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
    }

    private fun deleteData(id: String) {
        if (accountId != "") {
            viewModelScope.launch {
                try {
                    res = api.deleteTask(AccountData(accountId = accountId, id = id, password = ""))
                    Log.i("/fetchdeleteData", res.toString())
                } catch (e: Exception) {
                    Log.e("/fetchdeleteData_error", e.toString())
                }
            }
        }
    }

    private fun updateData(task: TasksData, id: String) {
        if (accountId != "") {
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
    }

    fun logout() {
        accountId = ""
        upgradeSelectedGroupTag("tag")
        _tasksList = mutableListOf<TasksData>().toMutableStateList()
        _todoTasksList = mutableListOf<TasksData>().toMutableStateList()
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
        if (type == "todo")
            _todoTasksList.removeAll { it.id == id }
        else
            _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
        deleteData(id)
    }

    fun upgradeGroupTag(type: String, id: String, value: String, name: String) {
        when (name) {
            "remove" -> {
                val item = _tasksList.find { it.id == id }
                item?.groupTag!!.remove(value)
                _tasksList.find { it.id == id }?.groupTag = item.groupTag
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                updateData(item, id)
            }
            "add" -> {
                val item = _tasksList.find { it.id == id }
                if (!item?.groupTag!!.contains(value)) {
                    item.groupTag.add(value)
                    _tasksList.find { it.id == id }?.groupTag = item.groupTag
                    _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                    updateData(item, id)
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
                _tasksList.find { it.id == id }?.let { it.toToday = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                updateData(item, id)
            }
            "title" -> {
                item!!.title = value as String
                _tasksList.find { it.id == id }?.let { it.title = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                updateData(item, id)
            }
            "isChecked" -> {
                if (value as Boolean) {
                    item!!.finishTime = LocalDateTime.now().toString()
                } else {
                    item!!.finishTime = null
                }
                item.isChecked = value
                _tasksList.find { it.id == id }?.let { it.isChecked = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                updateData(item, id)
            }
            "priority" -> {
                item!!.priority = value as Boolean
                _tasksList.find { it.id == id }?.let { it.priority = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                updateData(item, id)
            }
            "repeat" -> {
                item!!.repeat = value as Boolean
                _tasksList.find { it.id == id }?.let { it.repeat = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                updateData(item, id)
            }
            "setTaskTime" -> {
                item!!.setTaskTime = value as String
                _tasksList.find { it.id == id }?.let { it.setTaskTime = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                updateData(item, id)
            }
            "pomoTimes" -> {
                item!!.pomoTimes = value as Int
                _tasksList.find { it.id == id }?.let { it.pomoTimes = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                updateData(item, id)
            }
            "isRemindered" -> {
                item!!.isRemindered = value as Boolean
                _tasksList.find { it.id == id }?.let { it.isRemindered = value }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                updateData(item, id)
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
        if (type == "todo") {
            _todoTasksList.add(item)
        }
        addData(item)
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

    private fun changeAccountId(id: String) {
        accountId = id
        Log.i("/fetch_test_accountId", accountId)
    }

    fun sendLogInfo(id: String, password: String) {
        viewModelScope.launch {
            try {
                val msg: ReplyMessage =
                    accountApi.sendAccount(AccountData(id, password))
                Log.i("/fetch_account_log_reply", msg.toString())
                if (msg.code == 200) {
                    changeAccountId(id)
                    getAllData(id)
                } else {
                    changeLogFailFlag(true)
                }
            } catch (e: Exception) {
                Log.e("/fetch_account_log_reply_error", e.toString())
            }
        }
    }
}

