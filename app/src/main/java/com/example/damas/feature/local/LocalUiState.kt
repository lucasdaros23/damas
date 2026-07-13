package com.example.damas.feature.local

import com.example.damas.domain.model.Piece
import com.example.damas.domain.model.Square
import com.example.damas.domain.model.enums.PieceColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LocalUiState {
    val presentation = MutableStateFlow(value = Presentation())

    fun getSelectedSquare() = presentation.value.selectedSquare

    fun getAvailableMoves() = presentation.value.availableMoves

    fun getTurn() = presentation.value.turn

    fun getWinner() = presentation.value.winner

    fun setTurn(color: PieceColor){
        presentation.update { it.copy(turn = color) }
    }

    fun setTurnDefault(){
        setTurn(PieceColor.WHITE)
    }

    fun setSelectedSquare(square: Square?){
        presentation.update { it.copy(selectedSquare = square) }
    }

    fun setAvailableMoves(moves: List<Square>){
        presentation.update { it.copy(availableMoves = moves) }
    }

    fun fillBoard(board: List<Square>) {
        presentation.update { it.copy(board = board) }
    }

    fun setWinner(winner: PieceColor?) {
        presentation.update { it.copy(winner = winner) }
    }

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
    )
}