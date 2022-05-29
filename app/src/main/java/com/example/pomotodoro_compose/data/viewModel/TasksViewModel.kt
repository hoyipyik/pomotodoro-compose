package com.example.pomotodoro_compose.data.viewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomotodoro_compose.data.entity.TasksData
import com.example.pomotodoro_compose.data.database.TasksDatabase
import com.example.pomotodoro_compose.data.repository.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TasksViewModel(application: Application) : ViewModel() {
    private var _tasksList: LiveData<List<TasksData>>
    private var _todoTasksList: LiveData<List<TasksData>>
    private var repository: TasksRepository

    init {
        val tasksDao = TasksDatabase.getInstance(application).tasksDao()
        repository = TasksRepository(tasksDao)
        _tasksList = repository.fullTasksData
        _todoTasksList = repository.todoTasksData
    }

    val todoTasksList: LiveData<List<TasksData>>
        get() = _todoTasksList
    val boardTasksList: LiveData<List<TasksData>>
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
        viewModelScope.launch(Dispatchers.IO) {
            val item: TasksData = repository.getTaskById(id)
            repository.deleteTask(item)
        }
    }

    fun upgradeGroupTag(type: String, id: String, value: String, name: String){}
//    {
//        when (name) {
//            "remove" -> {
//                val item = _tasksList.find { it.id == id }
//                item?.groupTag!!.remove(value)
//                _tasksList.find { it.id == id }?.groupTag = item.groupTag
//                _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
//            }
//            "add" -> {
//                val item = _tasksList.find { it.id == id }
//                if (!item?.groupTag!!.contains(value)) {
//                    item.groupTag.add(value)
//                    _tasksList.find { it.id == id }?.groupTag = item.groupTag
//                    _todoTasksList = getTodoTasksList(_tasksList).toMutableStateList()
//                }
//            }
//        }
//        _changeTagListFlag = true
//    }

    fun upgradeTask(type: String, id: String, name: String, value: Any?) {
        viewModelScope.launch(Dispatchers.IO) {
            val item: TasksData = repository.getTaskById(id)
            when (name) {
                "toToday" -> {
                    item.toToday = value as Boolean
                    repository.updateTask(item)
                }
                "title" -> {
                    item.title = value as String
                    repository.updateTask(item)
                }
                "isChecked" -> {
                    item.isChecked = value as Boolean
                    repository.updateTask(item)
                }
                "priority" -> {
                    item.priority = value as Boolean
                    repository.updateTask(item)
                }
                "repeat" -> {
                    item.repeat = value as Boolean
                    repository.updateTask(item)
                }
                "setTaskTime" -> {
                    item.setTaskTime = value as String
                    repository.updateTask(item)
                }
                "pomoTimes" -> {
                    item.pomoTimes = value as Int
                    repository.updateTask(item)
                }
                "isRemindered" -> {
                    item.isRemindered = value as Boolean
                    repository.updateTask(item)
                }
            }
            _changeFlag = true
        }
    }

    fun addTask(
        type: String,
        text: String,
        pomoNum: Int = 0,
        groupTag: List<String> = listOf("tag")
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val id: String = LocalDateTime.now().toString()
            val toToday: Boolean = type == "todo"
            val title: String = text
            val pomoTimes: Int = pomoNum
            val groupTags: List<String> = groupTag
            val item = TasksData(
                id = id,
                toToday = toToday,
                title = title,
                pomoTimes = pomoTimes,
                groupTag = groupTags
            )
            repository.addTask(item)
        }
    }

    fun getItem(id: String = _selectedId): TasksData {
        return repository.getTaskById(id = id)
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
}

