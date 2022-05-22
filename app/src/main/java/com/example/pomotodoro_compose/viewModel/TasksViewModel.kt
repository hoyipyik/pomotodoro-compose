package com.example.pomotodoro_compose.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.pomotodoro_compose.data.TasksData
import com.example.pomotodoro_compose.data.getTasksList
import com.example.pomotodoro_compose.data.getTodoTasksList

class TasksViewModel : ViewModel() {
    private var _tasksList = getTasksList().toMutableStateList()
    private val tasksList: MutableList<TasksData>
        get() = _tasksList
    private var _todoTasksList = getTodoTasksList(tasksList).toMutableStateList()
    val todoTasksList: MutableList<TasksData>
        get() = _todoTasksList
//    private var _boardTasksList = getBoardTasksList(tasksList).toMutableStateList()
    private  var _boardTasksList = tasksList.toMutableStateList()
    val boardTasksList: MutableList<TasksData>
        get() = _boardTasksList
}

