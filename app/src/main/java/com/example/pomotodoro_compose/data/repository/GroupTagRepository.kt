package com.example.pomotodoro_compose.data.repository

import androidx.lifecycle.LiveData
import com.example.pomotodoro_compose.data.dao.GroupTagDao
import com.example.pomotodoro_compose.data.entity.GroupTagData

class GroupTagRepository(private val groupTagDao: GroupTagDao) {
    val allGroupTags: LiveData<List<GroupTagData>> = groupTagDao.getAllGroupTags()

    suspend fun insertGroupTag(groupTag: GroupTagData) {
        groupTagDao.insertGroupTag(groupTag)
    }

    suspend fun deleteGroupTag(groupTag: GroupTagData) {
        groupTagDao.deleteGroupTag(groupTag)
    }

    suspend fun deleteAllGroupTags() {
        groupTagDao.deleteAllGroupTags()
    }

    suspend fun updateGroupTag(groupTag: GroupTagData) {
        groupTagDao.updateGroupTag(groupTag)
    }

    fun getGroupTagById(id: String): GroupTagData {
        return groupTagDao.getGroupTagById(id)
    }
}