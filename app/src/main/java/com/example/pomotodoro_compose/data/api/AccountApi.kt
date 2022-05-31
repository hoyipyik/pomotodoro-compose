package com.example.pomotodoro_compose.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://123.56.107.143:9961"

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object AccountApi {
    val retrofitService: AccountApiService by lazy {
        retrofit.create(AccountApiService::class.java)
    }
}