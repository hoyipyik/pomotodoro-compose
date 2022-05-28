package com.example.pomotodoro_compose.data.database

import androidx.lifecycle.LiveData
import com.example.pomotodoro_compose.data.TasksData
import java.net.IDN

class TasksRepository(private val tasksDao: TasksDao) {
    val fullTasksData: LiveData<MutableList<TasksData>> = tasksDao.getAllTasks()
    suspend fun addTask(task: TasksData) {
        tasksDao.insertTask(task)
    }
    fun getTaskById(id: String): TasksData{
        return tasksDao.getTaskById(id = id)
    }

    suspend fun deleteTask(task: TasksData) {
        tasksDao.deleteTask(task)
    }
    suspend fun updateTask(task: TasksData) {
        tasksDao.updateTask(task)
    }
    suspend fun deleteAllTasks(){
        tasksDao.deleteAllTasks()
    }
}
