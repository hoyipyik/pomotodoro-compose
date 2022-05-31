package com.example.pomotodoro_compose.data.entity

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime

data class GroupTagListData(
    var groupTagName: String,
    var colour: Int,
    var tagId: String =  LocalDateTime.now().toString(),
    var accountId: String = ""
    )
