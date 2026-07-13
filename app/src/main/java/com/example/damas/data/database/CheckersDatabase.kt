package com.example.damas.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.damas.data.dao.PieceDao
import com.example.damas.data.entity.PieceEntity

@Database(
    entities = [PieceEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CheckersDatabase : RoomDatabase() {
    abstract fun pieceDao(): PieceDao
}