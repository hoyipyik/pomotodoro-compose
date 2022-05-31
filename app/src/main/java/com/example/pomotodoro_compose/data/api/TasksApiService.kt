package com.example.pomotodoro_compose.data.api

import com.example.pomotodoro_compose.data.entity.AccountData
import com.example.pomotodoro_compose.data.entity.TasksData
import com.example.pomotodoro_compose.data.entity.ToolData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TasksApiService {
    @POST("/get_full_tasks")
    suspend fun getFullTasksData(@Body accountInfoGetter: AccountData): MutableList<TasksData>

    @POST("/delete_task")
    suspend fun deleteTask(@Body accountInfoGetter: AccountData): Response<ResponseBody>

    @POST("/add_task")
    suspend fun addTask(@Body task: TasksData): Response<ResponseBody>

    @POST("/upgrade_task")
    suspend fun upgradeTask(@Body task: TasksData): Response<ResponseBody>
}
