package com.example.pomotodoro_compose.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tasks_table")
data class TasksData(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String = LocalDateTime.now().toString(),
    @ColumnInfo(name = "to_today")
    var toToday: Boolean = true,
    @ColumnInfo(name = "title")
    var title: String = "Task",
    @ColumnInfo(name = "is_checked")
    var isChecked: Boolean = false,
    @ColumnInfo(name = "priority")
    var priority: Boolean = false,
    @ColumnInfo(name = "repeat")
    var repeat: Boolean = false,
    @ColumnInfo(name = "finish_time")
    var finishTime: String? = null,
    @ColumnInfo(name = "is_remembered")
    var isRemindered: Boolean = false,
    @ColumnInfo(name = "set_task_time")
    var setTaskTime: String = "Set Task Time",
    @ColumnInfo(name = "pomo_times")
    var pomoTimes: Int = 0,
    @ColumnInfo(name = "group_tag")
    var groupTag: MutableList<String> = mutableListOf("tag"),
//    var subTasks: MutableList<SubTasksData>? = null
)


