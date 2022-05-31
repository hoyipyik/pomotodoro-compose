package com.example.pomotodoro_compose.container

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.R

@Composable
fun BlankContainer(type: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.sticker), contentDescription = null)
        if (type == "todo") {
            Text(text = "Add your todo tasks here")
        } else {
            Text(text = "Add tasks to the board")
        }
    }
}
