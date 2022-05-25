package com.example.pomotodoro_compose.viewModel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.data.getTasksList
import com.example.pomotodoro_compose.data.getTodoTasksList
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class TasksViewModel : ViewModel() {
    private var _tasksList = getTasksList().toMutableStateList()
    private var _changeFlag by mutableStateOf(false)
    val changeFlag
        get() = _changeFlag
    fun restoreChangeFlag(){
        _changeFlag = false
    }

    private var _selectedId: String by mutableStateOf("")
    val selectedId
        get() = _selectedId

    private var _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
    val todoTasksList: MutableList<TasksData>
        get() = _todoTasksList

    val boardTasksList: MutableList<TasksData>
        get() = _tasksList

    fun deleteTask(type: String, id: String) {
        _tasksList.removeAll { it.id == id }
        if(type == "todo")
            _todoTasksList.removeAll{ it.id == id}
        else
            _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
    }

    fun upgradeTask(type: String, id: String, name: String, value: Any) {
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

    fun addTask(type: String, text: String, pomoNum: Int = 0) {
        val id: String = LocalDateTime.now().toString()
        val toToday: Boolean = type == "todo"
        val title: String = text
        val pomoTimes: Int = pomoNum
        val item = TasksData(id = id, toToday = toToday, title = title, pomoTimes = pomoTimes)
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

    fun upgradeSelectedGroupTag(id: String){
        _selectedGroupTag = id
    }

    private fun createNotificationChannel(context: Context, head: String){
        val name = "Task notification"
        val descriptionText = "Task: $head. \n Time to work :)"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel  = NotificationChannel("Channel_id", name, importance).apply {
            description = descriptionText
        }

        val notificationManager : NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun notificationJudger(context: Context, currentTime: LocalTime){
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

