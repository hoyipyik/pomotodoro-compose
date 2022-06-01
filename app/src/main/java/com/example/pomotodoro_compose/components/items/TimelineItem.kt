package com.example.pomotodoro_compose.components.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.pomotodoro_compose.data.entity.TasksData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun TimelineItem(item: TasksData){
//    Text(text = item.title)
    Row() {
        Checkbox(checked = item.isChecked, enabled = false, onCheckedChange = {})
        Column(){
            Text(text = item.title)
            Column() {
                Text(text = "Added time: ${LocalDateTime.parse(item.id).hour}:${LocalDateTime.parse(item.id).minute} ")
                if(item.isChecked)
                Text(text = "Accomplished time: ${LocalDateTime.parse(item.finishTime).hour}:${LocalDateTime.parse(item.finishTime).minute}")
                if(item.setTaskTime != "Set Task Time")
                Text(text = "Scheduled time: ${item.setTaskTime}")
            }
        }
    }
}