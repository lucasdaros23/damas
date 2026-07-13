package com.example.damas.domain.model

data class Square(
    val x: Int,
    val y: Int,
    val piece: Piece? = null,
    val isClicked: Boolean = false,
    val isAvailableMove: Boolean = false,
) {
    val isDark: Boolean
        get() = (x+y) % 2 == 1

    val isOccupied: Boolean
        get() = piece != null
}
