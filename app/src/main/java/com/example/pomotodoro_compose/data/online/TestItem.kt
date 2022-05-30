package com.example.pomotodoro_compose.data.online

data class TestItem(
    val id: String,
    val title: String,
    val isChecked: Boolean =  false
)
