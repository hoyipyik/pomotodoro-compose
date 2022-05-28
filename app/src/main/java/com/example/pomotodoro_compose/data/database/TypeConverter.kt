package com.example.pomotodoro_compose.data.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun mutableListToString(value: MutableList<String>?): String {
        return value.toString()
    }

    @TypeConverter
    fun stringToMutableList(str: String?): MutableList<String> {
        return  str?.let {
            it.substring(1, it.length - 1).split(", ").toMutableList()
        } ?: mutableListOf()
    }
}