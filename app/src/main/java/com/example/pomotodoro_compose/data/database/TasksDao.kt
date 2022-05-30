package com.example.pomotodoro_compose.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pomotodoro_compose.data.TasksData

@Dao
interface TasksDao {
   @Query("SELECT * FROM tasks_table")
   fun getAllTasks(): LiveData<List<TasksData>>

    @Insert
   suspend fun insertTask(task: TasksData)

   @Query("SELECT * FROM tasks_table WHERE id = :id")
   fun getTaskById(id: String): TasksData
   @Update
   suspend fun updateTask(task: TasksData)

   @Delete
    suspend fun deleteTask(task: TasksData)

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAllTasks()
}