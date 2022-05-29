package com.example.pomotodoro_compose.data.database

import androidx.room.TypeConverter

class ListConverters {
    @TypeConverter
    fun listToString(value: List<String>?): String {
        return value.toString()
    }

    @TypeConverter
    fun stringToList(str: String?): List<String> {
        return  str?.let {
            it.substring(1, it.length - 1).split(", ").toList()
        } ?: listOf()
    }
}
