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
}

