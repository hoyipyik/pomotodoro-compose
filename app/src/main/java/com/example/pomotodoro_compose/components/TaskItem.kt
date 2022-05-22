package com.example.pomotodoro_compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.data.TasksData

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskItem(item: TasksData, type: String) {
    val id: String = item.id
    var toToday: Boolean = item.toToday
    var title: String = item.title
    var isChecked: Boolean = item.isChecked
    var groupTag: String? = item.groupTag
    var priority: Boolean = item.priority
    var finishTime: String? = item.finishTime
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.Center)
                .height(70.dp)
                .padding(top = 1.dp, bottom = 9.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable(onClick = { /* Ignoring onClick */ })
                .background(MaterialTheme.colors.onSecondary),
            verticalAlignment = Alignment.CenterVertically

        ) {
            if(type == "board" && toToday)
                Box(modifier = Modifier.border(5.dp, Color.Black).width(5.dp).fillMaxHeight())
            Checkbox(
                checked = isChecked,
                onCheckedChange = {},
                modifier = Modifier.padding(start = 4.dp)
            )
            Text(
                text = title, modifier = Modifier
                    .fillMaxWidth(0.82f)
                    .padding(start = 1.dp)
            )
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 4.dp)) {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
        }
    }
}