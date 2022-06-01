package com.example.pomotodoro_compose.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomotodoro_compose.data.entity.TasksData
import java.time.LocalDateTime

@Composable
fun TimelineItem(item: TasksData) {
//    Text(text = item.title)
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.onSecondary)
            .padding(7.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Spacer(modifier = Modifier.fillMaxWidth(0.05f))
        if (item.isChecked) {
            Icon(
                Icons.Filled.CheckCircle, contentDescription = "Task",
                Modifier.size(30.dp)
            )
        } else {
            Icon(
                Icons.Filled.RadioButtonUnchecked,
                contentDescription = "Task",
                Modifier.size(30.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        ) {
            if(item.isOverdue)
                Text(text = item.title, modifier = Modifier.padding(start = 5.dp), color = Color.Red)
            else
                Text(text = item.title, modifier = Modifier.padding(start = 5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    Icons.Filled.Schedule,
                    contentDescription = "timestamp",
                    Modifier
                        .size(20.dp)
                        .padding(start = 5.dp)
                )
                Text(
                    text = "${LocalDateTime.parse(item.id).hour}:${LocalDateTime.parse(item.id).minute} ",
                    modifier = Modifier.padding(start = 5.dp),
                    fontSize = 14.sp
                )
                if (item.isChecked) {
                    Icon(
                        Icons.Filled.SentimentSatisfied,
                        contentDescription = "timestamp",
                        Modifier
                            .size(20.dp)
                            .padding(start = 5.dp)
                    )
                    Text(
                        text = "${LocalDateTime.parse(item.finishTime).hour}:${
                            LocalDateTime.parse(
                                item.finishTime
                            ).minute
                        }", modifier = Modifier.padding(start = 5.dp), fontSize = 14.sp
                    )
                }

                if (item.setTaskTime != "Set Task Time") {
                    Icon(
                        Icons.Filled.Notifications,
                        contentDescription = "timestamp",
                        Modifier
                            .size(20.dp)
                            .padding(start = 5.dp)
                    )
                    Text(
                        text = item.setTaskTime,
                        modifier = Modifier.padding(start = 5.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewItem() {
    TimelineItem(
        item = TasksData(
            id = "2020-05-05T12:00:00",
            title = "Task 1",
            isChecked = true,
            finishTime = "2020-05-05T12:00:00",
            setTaskTime = "Set Task Time"
        )
    )
}

