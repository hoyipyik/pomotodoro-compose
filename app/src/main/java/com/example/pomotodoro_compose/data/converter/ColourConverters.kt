package com.example.pomotodoro_compose.data.converter

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter

class ColourConverters {
    @TypeConverter
    fun colourToInt(value: Color): Int {
        return value.toArgb()
    }

    @TypeConverter
    fun intToColour(str: Int?): Color {
        return Color(str!!)
    }
}