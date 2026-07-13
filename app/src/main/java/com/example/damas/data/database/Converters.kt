package com.example.damas.data.database

import com.example.damas.domain.model.enums.PieceColor
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromPieceColor(value: PieceColor): String {
        return value.name
    }

    @TypeConverter
    fun toPieceColor(value: String): PieceColor {
        return PieceColor.valueOf(value)
    }
}