package com.example.damas.domain.repository

import com.example.damas.data.entity.PieceEntity
import com.example.damas.domain.model.Piece
import com.example.damas.domain.model.enums.PieceColor
import kotlinx.coroutines.flow.Flow

interface PieceRepository {
    fun getPiecesByColor(color: PieceColor): Flow<List<Piece>>

    suspend fun getPieceById(id: Int): Piece?
    suspend fun getPieceByPosition(x: Int, y: Int): Piece?

    suspend fun movePiece(id: Int, x: Int, y: Int)
    suspend fun capturePiece(id: Int)
    suspend fun promotePiece(id: Int)

    suspend fun initializeBoard()
    suspend fun clearBoard()
}