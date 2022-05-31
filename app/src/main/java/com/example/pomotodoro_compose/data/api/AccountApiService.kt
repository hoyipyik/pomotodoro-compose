package com.example.pomotodoro_compose.data.api

import com.example.pomotodoro_compose.data.entity.AccountData
import com.example.pomotodoro_compose.data.entity.ReplyMessage
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountApiService {
    @POST("/account")
    suspend fun sendAccount(@Body item: AccountData): ReplyMessage

    @POST("/account_delete")
    suspend fun deleteAccount(@Body item: AccountData): Response<ResponseBody>

    @POST("/account_clear_all_data")
    suspend fun clearAllData(@Body item: AccountData): Response<ResponseBody>
}