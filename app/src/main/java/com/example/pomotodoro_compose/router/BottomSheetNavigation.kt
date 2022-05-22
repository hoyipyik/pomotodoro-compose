package com.example.pomotodoro_compose.router

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetNavigation(){
    Column( horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(6.dp))
        Box(modifier = Modifier
            .fillMaxWidth(0.35f)
            .height(3.dp)
            .border(2.dp, Color.LightGray)
            .clip(RoundedCornerShape(10.dp))
            .padding(vertical = 5.dp))
        LazyColumn {
            items(2) {
                ListItem(
                    text = { Text("Item $it") },
                    icon = {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                )
            }
        }
    }

}