package com.example.damas.domain.model

import com.example.damas.domain.model.enums.PieceColor

data class Piece(
    val id: Int = 0,
    val x: Int,
    val y: Int,
    val isAlive: Boolean = true,
    val isKing: Boolean = false,
    val color: PieceColor
)
