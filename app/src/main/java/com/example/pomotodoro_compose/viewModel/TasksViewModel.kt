package com.example.pomotodoro_compose.viewModel

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.data.getTasksList
import com.example.pomotodoro_compose.data.getTodoTasksList

class TasksViewModel : ViewModel() {
    private var _tasksList = getTasksList().toMutableStateList()

    private var _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
    val todoTasksList: MutableList<TasksData>
        get() = _todoTasksList

    val boardTasksList: MutableList<TasksData>
        get() = _tasksList

    fun delteTask(type: String, id: String){
        _tasksList.removeAll{ it.id == id }
        _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
    }

//    fun upgradeToToday(type: String, id: String, value: Boolean){
//        _tasksList.find { it.id == id }?.let { it.toToday = value }
//        _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
//    }

    fun upgradeTask(type: String, id: String, name: String, value: Any){
        when(name){
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
            "hasReminder" -> {
                _tasksList.find { it.id == id }?.let { it.hasReminder = value as Boolean }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
            "pomoTimes" -> {
                _tasksList.find { it.id == id }?.let { it.pomoTimes = value as Int }
                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
            }
        }
    }
}

