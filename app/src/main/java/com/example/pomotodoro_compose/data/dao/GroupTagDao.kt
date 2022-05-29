package com.example.pomotodoro_compose.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pomotodoro_compose.data.entity.GroupTagData

@Dao
interface GroupTagDao {
    @Query("SELECT * FROM group_tag_table")
    fun getAllGroupTags(): LiveData<List<GroupTagData>>

    @Insert
    suspend fun insertGroupTag(groupTagData: GroupTagData)

    @Delete
    suspend fun deleteGroupTag(groupTagData: GroupTagData)

    @Update
    suspend fun updateGroupTag(groupTagData: GroupTagData)

    @Query("DELETE FROM group_tag_table")
    suspend fun deleteAllGroupTags()

    @Query("SELECT * FROM group_tag_table WHERE group_tag_id = :Id")
    fun getGroupTagById(Id: String): GroupTagData
}