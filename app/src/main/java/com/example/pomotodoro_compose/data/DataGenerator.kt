package com.example.pomotodoro_compose.data

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.example.pomotodoro_compose.ui.theme.Bluelight
import com.example.pomotodoro_compose.ui.theme.Purple500
import com.example.pomotodoro_compose.ui.theme.Purple700
import java.time.LocalDateTime

/*
*  Used to generate a dummy data used for testing
* */
fun getTasksList(): MutableList<TasksData> {
    Log.i("/upgrade", "task generated")
    return MutableList(5){ i ->
        var flag:Boolean = false
        if(i%2 == 0)  {flag = true}
        if(i == 0){
            TasksData(id = i.toString(), title = "Task $i", isChecked = false, toToday = flag, groupTag = mutableListOf("tag", "tag3", "tag2"))
        }else if(i == 1){
            TasksData(id = i.toString(), title = "Task $i", isChecked = false, toToday = flag, groupTag = mutableListOf("tag2", "tag"))
        }else
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
        GroupTagListData(groupTagName = "All", colour = Purple500, tagId = "tag"),
        GroupTagListData(groupTagName = "Android", colour = Purple700, tagId = "tag3"),
        GroupTagListData(groupTagName = "Python", colour = Bluelight, tagId = "tag2"),
//        GroupTagListData(groupTagName = "Life", colour = Color.Yellow),
//        GroupTagListData(groupTagName = "Open Flow", colour = Color.Magenta),
    )
}