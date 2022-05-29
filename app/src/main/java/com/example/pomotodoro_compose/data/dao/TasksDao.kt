package com.example.pomotodoro_compose.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pomotodoro_compose.data.entity.TasksData

@Dao
interface TasksDao {
   @Query("SELECT * FROM tasks_table")
   fun getAllTasks(): LiveData<List<TasksData>>

   @Insert
   suspend fun insertTask(task: TasksData)

   @Query("SELECT * FROM tasks_table WHERE id = :id")
   fun getTaskById(id: String): TasksData

   @Query("SELECT * FROM tasks_table WHERE to_today = 1")
   fun getTodoData(): LiveData<List<TasksData>>

   @Update
   suspend fun updateTask(task: TasksData)

   @Delete
    suspend fun deleteTask(task: TasksData)

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAllTasks()
}