package com.example.pomotodoro_compose.data.entity

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "group_tag_table")
data class GroupTagData(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "group_tag_id")
    var tagId: String = LocalDateTime.now().toString(),
    @ColumnInfo(name = "group_tag_name")
    var groupTagName: String,
    @ColumnInfo(name = "group_tag_colour")
    var colour: Color,
)
