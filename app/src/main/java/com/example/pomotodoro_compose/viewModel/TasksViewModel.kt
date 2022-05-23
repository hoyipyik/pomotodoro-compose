package com.example.pomotodoro_compose.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.data.getTasksList
import com.example.pomotodoro_compose.data.getTodoTasksList
import java.time.LocalDateTime

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
            "groupTag" -> {
                _tasksList.find { it.id == id }?.let { it.groupTag = value as String }
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
}

