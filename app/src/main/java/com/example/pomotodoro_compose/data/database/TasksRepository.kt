package com.example.pomotodoro_compose.data.database

import com.example.pomotodoro_compose.data.TasksData

class TasksRepository(private val tasksDao: TasksDao) {
    val fullTasksData: MutableList<TasksData> = tasksDao.getAllTasks()
    suspend fun addTask(task: TasksData) {
        tasksDao.insertTask(task)
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
