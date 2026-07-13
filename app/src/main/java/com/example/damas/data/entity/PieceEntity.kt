package com.example.damas.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pieces")
data class PieceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val x: Int,
    val y: Int,
    @ColumnInfo(name = "is_alive")
    val isAlive: Boolean = true,
    @ColumnInfo(name = "is_king")
    val isKing: Boolean = false,
    @ColumnInfo(name = "color")
    val color: String
)