package com.example.damas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.damas.data.entity.PieceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PieceDao {
    @Query("SELECT * FROM pieces WHERE is_alive = 1 AND color = :color")
    fun getPiecesByColor(color: String): Flow<List<PieceEntity>>

    @Query("SELECT * FROM pieces WHERE id = :id")
    suspend fun getPieceById(id: Int) : PieceEntity?

    @Query("SELECT * FROM pieces WHERE x = :x AND y = :y AND is_alive = 1")
    suspend fun getPieceByPosition(x: Int, y: Int): PieceEntity?

    @Query("UPDATE pieces SET x = :x, y = :y WHERE id = :id")
    suspend fun movePiece(id: Int, x: Int, y: Int): Int

    @Query("UPDATE pieces SET is_alive = 0 WHERE id = :id")
    suspend fun capturePiece(id: Int): Int

    @Query("UPDATE pieces SET is_king = 1 WHERE id = :id")
    suspend fun promotePiece(id: Int): Int

    @Insert
    suspend fun insertAll(pieces: List<PieceEntity>)

    @Query("DELETE FROM pieces")
    suspend fun clearBoard(): Int
}