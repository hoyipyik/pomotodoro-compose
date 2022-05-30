package com.example.pomotodoro_compose.data.online

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

private const val BASE_URL = "http://123.56.107.143:9961"

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface TasksApiService {
    @POST("/test.json")
    suspend fun getTestMessage(): List<TestItem>
}

object TasksApi {
    val retrofitService: TasksApiService by lazy {
        retrofit.create(TasksApiService::class.java)
    }
}