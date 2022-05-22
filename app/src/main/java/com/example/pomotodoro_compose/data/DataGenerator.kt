package com.example.pomotodoro_compose.data

import android.util.Log
import androidx.compose.ui.graphics.Color

/*
*  Used to generate a dummy data used for testing
* */
fun getTasksList(): MutableList<TasksData> {
    Log.i("/upgrade", "task generated")
    return MutableList(30){ i ->
        var flag:Boolean = false
        if(i%2 == 0)  {flag = true}
        TasksData(id = i.toString(), title = "Task $i", isChecked = false, toToday = flag)
    }
}

fun getTodoTasksList(list:MutableList<TasksData>): MutableList<TasksData>{
    val result: MutableList<TasksData> = list.toMutableList()
    result.removeAll{ !it.toToday }
    return  result
}

fun getGroupTagList(): MutableList<GroupTagListData> {
    return mutableListOf(
        GroupTagListData(groupTagName = "English", colour = Color.Green),
        GroupTagListData(groupTagName = "Android", colour = Color.DarkGray),
        GroupTagListData(groupTagName = "Python", colour = Color.Red),
        GroupTagListData(groupTagName = "Life", colour = Color.Yellow),
        GroupTagListData(groupTagName = "Open Flow", colour = Color.Magenta),
    )
}