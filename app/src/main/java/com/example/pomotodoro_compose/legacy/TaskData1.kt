package com.example.pomotodoro_compose.legacy

import com.example.pomotodoro_compose.legacy.SubTasksData

data class TasksData1(
    val id: String = "2019210737",
    var toToday: Boolean = true,
    var title: String = "Task",
    var isChecked: Boolean = false,
    var groupTag: MutableList<String> = mutableListOf("tag"),
    var priority: Boolean = false,
    var repeat: Boolean = false,
    var finishTime: String? = null,
    var isRemindered: Boolean = false,
    var setTaskTime: String = "Set Task Time",
    var pomoTimes: Int = 0,
    var subTasks: MutableList<SubTasksData>? = null
)


