package com.example.damas.data.mapper

import com.example.damas.data.entity.PieceEntity
import com.example.damas.domain.model.Piece
import com.example.damas.domain.model.enums.PieceColor

object PieceMapper {
    fun PieceEntity.toDomain() =
        Piece(
            id = id,
            x = x,
            y = y,
            isAlive = isAlive,
            isKing = isKing,
            color = PieceColor.valueOf(color),
        )

    fun Piece.toEntity() =
        PieceEntity(
            id = id,
            x = x,
            y = y,
            isAlive = isAlive,
            isKing = isKing,
            color = color.name,
        )
}