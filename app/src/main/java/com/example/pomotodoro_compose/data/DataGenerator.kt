package com.example.pomotodoro_compose.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.pomotodoro_compose.data.entity.GroupTagData
import com.example.pomotodoro_compose.data.entity.TasksData
import com.example.pomotodoro_compose.ui.theme.Bluelight
import com.example.pomotodoro_compose.ui.theme.Purple500
import com.example.pomotodoro_compose.ui.theme.Purple700

/*
*  Used to generate a dummy data used for testing
* */
fun getTasksList(): List<TasksData> {
    Log.i("/upgrade", "task generated")
    return List(5){ i ->
        var flag:Boolean = false
        if(i%2 == 0)  {flag = true}
        if(i == 0){
            TasksData(id = i.toString(), title = "Task $i", isChecked = false, toToday = flag, groupTag = listOf("tag", "tag3", "tag2"))
        }else if(i == 1){
            TasksData(id = i.toString(), title = "Task $i", isChecked = false, toToday = flag, groupTag = listOf("tag2", "tag"))
        }else
        TasksData(id = i.toString(), title = "Task $i", isChecked = false, toToday = flag)
    }
}

fun getBoardTasksList(list: LiveData<List<TasksData>>): List<TasksData> {
    val result = list.value
    return result!!
}

//fun getTodoTasksList(list:List<TasksData>): List<TasksData>{
//    val result: List<TasksData> = list.toList()
//    result.removeAll{ !it.toToday }
//    return  result
//}

fun getGroupTagList(): List<GroupTagData> {
    return listOf(
        GroupTagData(groupTagName = "All", colour = Purple500, tagId = "tag"),
        GroupTagData(groupTagName = "Android", colour = Purple700, tagId = "tag3"),
        GroupTagData(groupTagName = "Python", colour = Bluelight, tagId = "tag2"),
//        GroupTagListData(groupTagName = "Life", colour = Color.Yellow),
//        GroupTagListData(groupTagName = "Open Flow", colour = Color.Magenta),
    )
}