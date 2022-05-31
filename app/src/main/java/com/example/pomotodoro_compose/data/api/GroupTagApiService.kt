package com.example.pomotodoro_compose.data.api

import com.example.pomotodoro_compose.data.entity.AccountData
import com.example.pomotodoro_compose.data.entity.GroupTagListData
import com.example.pomotodoro_compose.data.entity.ToolData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GroupTagApiService {
    @POST("/get_full_group_tag")
    suspend fun getGroupTag(@Body accountId: AccountData): MutableList<GroupTagListData>

    @POST("/delete_group_tag")
    suspend fun deleteGroupTag(@Body groupTagId: AccountData): Response<ResponseBody>

    @POST("/update_group_tag")
    suspend fun updateGroupTag(@Body groupTag: GroupTagListData): Response<ResponseBody>

    @POST("/add_group_tag")
    suspend fun addGroupTag(@Body groupTag: GroupTagListData): Response<ResponseBody>
}