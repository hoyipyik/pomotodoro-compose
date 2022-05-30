package com.example.pomotodoro_compose.data.viewModel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.data.database.TasksDatabase
import com.example.pomotodoro_compose.data.database.TasksRepository
import com.example.pomotodoro_compose.data.getRoomDatebase
import com.example.pomotodoro_compose.data.getTasksList
import com.example.pomotodoro_compose.data.getTodoTasksList
import java.time.LocalDateTime
import java.time.LocalTime

class TasksViewModel(application: Application) : ViewModel() {
//    private var _tasksList = getBoardTasksList(_holderList).toMutableStateList()
    private var _tasksList = getTasksList().toMutableStateList()
    private var _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()

    val todoTasksList: MutableList<TasksData>
        get() = _todoTasksList

    val boardTasksList: MutableList<TasksData>
        get() = _tasksList

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
    }

    fun upgradeGroupTag(type: String, id: String, value: String, name: String) {
        when (name) {
            "remove" -> {
                val item = _tasksList.find { it.id == id }
                item?.groupTag!!.remove(value)
                _tasksList.find { it.id == id }?.groupTag = item.groupTag
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "add" -> {
                val item = _tasksList.find { it.id == id }
                if (!item?.groupTag!!.contains(value)) {
                    item.groupTag.add(value)
                    _tasksList.find { it.id == id }?.groupTag = item.groupTag
                    _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
                }
            }
        }
        _changeTagListFlag = true
    }

    fun upgradeTask(type: String, id: String, name: String, value: Any?) {
        when (name) {
            "toToday" -> {
                _tasksList.find { it.id == id }?.let { it.toToday = value as Boolean }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "title" -> {
                _tasksList.find { it.id == id }?.let { it.title = value as String }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "isChecked" -> {
                _tasksList.find { it.id == id }?.let { it.isChecked = value as Boolean }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "priority" -> {
                _tasksList.find { it.id == id }?.let { it.priority = value as Boolean }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "repeat" -> {
                _tasksList.find { it.id == id }?.let { it.repeat = value as Boolean }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "setTaskTime" -> {
                _tasksList.find { it.id == id }?.let { it.setTaskTime = value as String }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "pomoTimes" -> {
                _tasksList.find { it.id == id }?.let { it.pomoTimes = value as Int }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "isRemindered" -> {
                _tasksList.find { it.id == id }?.let { it.isRemindered = value as Boolean }
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
