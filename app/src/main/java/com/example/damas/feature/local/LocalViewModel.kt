package com.example.damas.feature.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.damas.domain.model.Piece
import com.example.damas.domain.model.Square
import com.example.damas.domain.model.enums.PieceColor
import com.example.damas.domain.repository.PieceRepository
import com.example.damas.feature.local.LocalUiEvent.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class LocalViewModel @Inject constructor(
    private val repository: PieceRepository,
    val uiState: LocalUiState,
    val uiEvent: LocalUiEvent,
) : ViewModel() {
    private val whitePieces: StateFlow<List<Piece>> =
        repository
            .getPiecesByColor(PieceColor.WHITE)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )

    private val blackPieces: StateFlow<List<Piece>> =
        repository
            .getPiecesByColor(PieceColor.BLACK)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )

    init {
        initFillBoard()
        startGame()
    }

    fun onActionEvent(action: LocalScreenAction) {
        action.fold(
            resetButtonClickedAction = ::startGame,
            closeButtonClickedAction = ::closeButtonClickedAction,
            squareClickedAction = ::squareClickedAction,
        )
    }

    private fun startGame() {
        viewModelScope.launch {
            repository.clearBoard()
            repository.initializeBoard()
            uiState.setTurnDefault()
            uiState.setSelectedSquare(null)
            uiState.setWinner(null)
        }
    }

    private fun closeButtonClickedAction() {
        uiEvent.send(ScreenEvent.NavigateBack)
    }

    private fun squareClickedAction(square: Square) {
        if (uiState.getWinner() == null) viewModelScope.launch {
            val selectedSquare = uiState.getSelectedSquare()
            val availableMoves = uiState.getAvailableMoves()
            val pieces =
                if (uiState.getTurn() == PieceColor.WHITE) whitePieces.value else blackPieces.value

            val destinationMove = availableMoves.find { it.x == square.x && it.y == square.y }

            val piecesCanCapture = mutableListOf<Piece>()
            for (piece in pieces) {
                if (getCaptureMoves(piece).isNotEmpty()) {
                    piecesCanCapture.add(piece)
                }
            }

            if (selectedSquare != null && destinationMove != null) {
                executeMove(selectedSquare, destinationMove)
            } else {
                val piece = getPieceByPosition(square.x, square.y)
                if (
                    piece != null &&
                    piece.color == uiState.getTurn() &&
                    selectedSquare != square &&
                    (piecesCanCapture.isEmpty() || piecesCanCapture.contains(piece))
                ) {
                    uiState.setSelectedSquare(square)
                    val moves = calculateMovesForPiece(piece)
                    uiState.setAvailableMoves(moves)
                } else {
                    uiState.setSelectedSquare(null)
                    uiState.setAvailableMoves(emptyList())
                }
            }
        }
    }

    private suspend fun calculateMovesForPiece(piece: Piece): List<Square> {
        val captureMoves = getCaptureMoves(piece)
        val simpleMoves = getSimpleMoves(piece)
        val finalCoords = if (captureMoves.isNotEmpty()) captureMoves else simpleMoves
        val currentBoard = uiState.presentation.value.board
        return finalCoords.mapNotNull { (nx, ny) ->
            currentBoard.find { it.x == nx && it.y == ny }
        }
    }

    private suspend fun getSimpleMoves(piece: Piece): List<Pair<Int, Int>> = buildList {
        if (!piece.isKing) {
            val dy = if (piece.color == PieceColor.WHITE) -1 else 1
            for (dx in listOf(-1, 1)) {
                val newX = piece.x + dx
                val newY = piece.y + dy
                if (newX in 0..7 && newY in 0..7) {
                    val targetPiece = getPieceByPosition(newX, newY)
                    if (targetPiece == null) {
                        add(newX to newY)
                    }
                }
            }
        } else {
            val directions = listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
            for ((dx, ddy) in directions) {
                var nx = piece.x + dx
                var ny = piece.y + ddy
                while (nx in 0..7 && ny in 0..7) {
                    if (getPieceByPosition(nx, ny) == null) {
                        add(nx to ny)
                    } else break
                    nx += dx
                    ny += ddy
                }
            }
        }
    }

    private suspend fun getCaptureMoves(piece: Piece): List<Pair<Int, Int>> = buildList {
        val directions = listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
        for ((dx, dy) in directions) {
            if (!piece.isKing) {
                val enemyX = piece.x + dx
                val enemyY = piece.y + dy
                val landingX = piece.x + (dx * 2)
                val landingY = piece.y + (dy * 2)

                if (landingX in 0..7 && landingY in 0..7) {
                    val enemy = getPieceByPosition(enemyX, enemyY)
                    val landing = getPieceByPosition(landingX, landingY)
                    if (enemy != null && enemy.color != piece.color && landing == null) {
                        add(landingX to landingY)
                    }
                }
            } else {
                var nx = piece.x + dx
                var ny = piece.y + dy
                var foundEnemy = false
                while (nx in 0..7 && ny in 0..7) {
                    val target = getPieceByPosition(nx, ny)
                    if (!foundEnemy) {
                        if (target != null) {
                            if (target.color != piece.color) foundEnemy = true
                            else break
                        }
                    } else {
                        if (target == null) add(nx to ny)
                        else break
                    }
                    nx += dx
                    ny += dy
                }
            }
        }
    }

    private fun initFillBoard() {
        viewModelScope.launch {
            combine(whitePieces, blackPieces) { white, black ->
                white + black
            }.collect { allPieces ->
                fillBoard(allPieces)
            }
        }
    }

    private fun fillBoard(pieces: List<Piece>) {
        viewModelScope.launch {
            uiState.fillBoard(
                buildList {
                    for (index in 0..63) {
                        val x = index % 8
                        val y = index / 8
                        add(
                            Square(
                                x = x,
                                y = y,
                                piece = pieces.find { it.x == x && it.y == y }
                            )
                        )
                    }
                }
            )
        }
    }

    private suspend fun executeMove(from: Square, to: Square) {
        val piece = from.piece ?: return
        val dx = to.x - from.x
        val dy = to.y - from.y

        var pieceJumped: Piece? = null
        if (abs(dx) >= 2) {
            val stepX = dx / abs(dx)
            val stepY = dy / abs(dy)
            var checkX = from.x + stepX
            var checkY = from.y + stepY
            while (checkX != to.x) {
                val p = getPieceByPosition(checkX, checkY)
                if (p != null) {
                    pieceJumped = p
                    break
                }
                checkX += stepX
                checkY += stepY
            }
        }

        movePiece(piece.id, to.x, to.y)
        pieceJumped?.let { capturePiece(it.id) }
        val wasCapture = pieceJumped != null

        val movedPiece = piece.copy(x = to.x, y = to.y)
        val pieceCanStillCapture =
            if (wasCapture) getCaptureMoves(movedPiece).isNotEmpty() else false
        val nextTurn = if (pieceCanStillCapture) uiState.getTurn() else
            if (uiState.getTurn() == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE
        uiState.setTurn(nextTurn)
        uiState.setSelectedSquare(if (pieceCanStillCapture) to.copy(piece = movedPiece) else null)
        uiState.setAvailableMoves(if (pieceCanStillCapture) calculateMovesForPiece(movedPiece) else emptyList())
        if (!pieceCanStillCapture) makeKing(movedPiece)
        checkWin()
    }

    private suspend fun makeKing(piece: Piece) {
        if (
            (piece.color == PieceColor.WHITE && piece.y == 0) ||
            (piece.color == PieceColor.BLACK && piece.y == 7)
        ) promotePiece(piece.id)
    }

    private fun checkWin() {
        if (whitePieces.value.isEmpty())uiState.setWinner(PieceColor.BLACK)
        if (blackPieces.value.isEmpty())uiState.setWinner(PieceColor.WHITE)
    }

    private suspend fun getPieceByPosition(x: Int, y: Int): Piece? = repository.getPieceByPosition(x, y)

    private suspend fun movePiece(id: Int, x: Int, y: Int) = repository.movePiece(id, x, y)

    private suspend fun capturePiece(id: Int) = repository.capturePiece(id)

    private suspend fun promotePiece(id: Int) = repository.promotePiece(id)
}
