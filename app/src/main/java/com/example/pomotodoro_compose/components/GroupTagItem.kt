package com.example.pomotodoro_compose.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GroupTagItem(name: String, colour: Color) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.padding(end = 5.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = colour)
    ) {
        Text(text = name, color = Color.White)
    }
}
