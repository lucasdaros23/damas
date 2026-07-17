package com.example.damas.feature.local

import com.example.damas.core.EMPTY_STRING
import com.example.damas.domain.model.Dialog
import com.example.damas.domain.model.Piece
import com.example.damas.domain.model.Square
import com.example.damas.domain.model.enums.PieceColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LocalUiState {
    val presentation = MutableStateFlow(value = Presentation())

    fun update(transform: (Presentation) -> Presentation) = presentation.update(transform)

    data class Presentation(
        val board: List<Square> = emptyList(),
        val alivePieces: List<Piece> = emptyList(),
        val whitePieces: List<Piece> = emptyList(),
        val blackPieces: List<Piece> = emptyList(),
        val turn: PieceColor = PieceColor.WHITE,
        val boardEnabled: Boolean = true,
        val availableMoves: List<Square> = emptyList(),
        val selectedSquare: Square? = null,
        val winner: PieceColor? = null,
        val activeDialog: Dialog? = null,
        val resetButtonText: String = EMPTY_STRING,
        val titleMessage: String = EMPTY_STRING,
    )
}