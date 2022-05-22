package com.example.pomotodoro_compose.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddTask(type: String, modifier: Modifier) {
    Text(text = "This is Add task $type", modifier = modifier)
}