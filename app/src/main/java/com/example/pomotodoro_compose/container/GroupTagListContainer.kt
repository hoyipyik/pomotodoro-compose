package com.example.pomotodoro_compose.container

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.components.GroupTagList

@Composable
fun GroupTagListContainer() {
    Box(modifier = Modifier.fillMaxHeight(0.08f)) {
        Row(
            modifier = Modifier.fillMaxWidth(0.87f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.85f)) {
                GroupTagList(modifier = Modifier)
            }
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 9.dp)) {
                Icon(Icons.Filled.Backspace, contentDescription = null)
            }
        }
    }
}


