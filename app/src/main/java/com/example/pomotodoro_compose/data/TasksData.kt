package com.example.pomotodoro_compose.data

data class TasksData(
    val id: String = "2019210737",
    var toToday: Boolean = true,
    var title: String = "Task",
    var isChecked: Boolean = false,
    var groupTag: String? = null,
    var priority: Boolean = false,
    var repeat: Boolean = false,
    var finishTime: String? = null,
    var hasReminder: Boolean = false,
    var pomoTimes: Int = 2,
    var subTasks: MutableList<SubTasksData>? = null
)


