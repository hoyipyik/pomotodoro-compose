package com.example.pomotodoro_compose.components.grouptag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.data.GroupTagListData

@Composable
fun EditGroupTagItem(item: GroupTagListData) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 6.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.onSecondary),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Box(
                    Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(item.colour)
                )
            }
            Text(item.groupTagName, modifier = Modifier.fillMaxWidth(0.7f), textAlign = TextAlign.Center)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Delete, contentDescription = null)
            }
        }
    }
}