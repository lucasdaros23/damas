package com.example.damas.feature.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.damas.domain.model.Piece
import com.example.damas.domain.model.Square
import com.example.damas.domain.model.enums.PieceColor
import com.example.damas.domain.repository.PieceRepository
import com.example.damas.feature.local.LocalUiEvent.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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
        if (uiState.getWinner() == null) viewModelScope.launch(Dispatchers.Default) {
            val selectedSquare = uiState.getSelectedSquare()
            val availableMoves = uiState.getAvailableMoves()
            val currentPieces = whitePieces.value + blackPieces.value
            val turn = uiState.getTurn()

            val destinationMove = availableMoves.find { it.x == square.x && it.y == square.y }
            val piecesCanCapture = (if (turn == PieceColor.WHITE) whitePieces.value else blackPieces.value)
                .filter { getCaptureMoves(it, currentPieces).isNotEmpty() }

            if (selectedSquare != null && destinationMove != null) {
                executeMove(selectedSquare, destinationMove)
            } else {
                val piece = getPieceByPosition(square.x, square.y, currentPieces)
                if (piece != null && piece.color == turn &&
                    (piecesCanCapture.isEmpty() || piecesCanCapture.any { it.id == piece.id })) {

                    uiState.setSelectedSquare(square)
                    val moves = calculateMovesForPiece(piece, currentPieces)
                    uiState.setAvailableMoves(moves)
                } else {
                    uiState.setSelectedSquare(null)
                    uiState.setAvailableMoves(emptyList())
                }
            }
        }
    }
    private suspend fun calculateMovesForPiece(piece: Piece, pieces: List<Piece>): List<Square> {
        val captureMoves = getCaptureMoves(piece, pieces)
        val simpleMoves = getSimpleMoves(piece, pieces)
        val finalCoords = captureMoves.ifEmpty { simpleMoves }
        val currentBoard = uiState.presentation.value.board
        return finalCoords.mapNotNull { (nx, ny) ->
            currentBoard.find { it.x == nx && it.y == ny }
        }
    }

    private fun getSimpleMoves(piece: Piece, pieces: List<Piece>): List<Pair<Int, Int>> = buildList {
        if (!piece.isKing) {
            val dy = if (piece.color == PieceColor.WHITE) -1 else 1
            for (dx in listOf(-1, 1)) {
                val nx = piece.x + dx
                val ny = piece.y + dy
                if (nx in 0..7 && ny in 0..7 && getPieceByPosition(nx, ny, pieces) == null) {
                    add(nx to ny)
                }
            }
        } else {
            val directions = listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
            for ((dx, dy) in directions) {
                var nx = piece.x + dx
                var ny = piece.y + dy
                while (nx in 0..7 && ny in 0..7) {
                    if (getPieceByPosition(nx, ny, pieces) == null) add(nx to ny) else break
                    nx += dx
                    ny += dy
                }
            }
        }
    }
    private fun getCaptureMoves(piece: Piece, pieces: List<Piece>): List<Pair<Int, Int>> = buildList {
        val directions = listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)
        for ((dx, dy) in directions) {
            if (!piece.isKing) {
                val ex = piece.x + dx
                val ey = piece.y + dy
                val lx = piece.x + (dx * 2)
                val ly = piece.y + (dy * 2)
                if (lx in 0..7 && ly in 0..7) {
                    val enemy = getPieceByPosition(ex, ey, pieces)
                    val landing = getPieceByPosition(lx, ly, pieces)
                    if (enemy != null && enemy.color != piece.color && landing == null) add(lx to ly)
                }
            } else {
                var nx = piece.x + dx
                var ny = piece.y + dy
                var foundEnemy: Piece? = null
                while (nx in 0..7 && ny in 0..7) {
                    val target = getPieceByPosition(nx, ny, pieces)
                    if (foundEnemy == null) {
                        if (target != null) {
                            if (target.color != piece.color) foundEnemy = target else break
                        }
                    } else {
                        if (target == null) add(nx to ny) else break
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
        uiState.fillBoard(
            List(64) { index ->
                val x = index % 8
                val y = index / 8
                Square(x, y, pieces.find { it.x == x && it.y == y })
            }
        )
    }

    private suspend fun executeMove(from: Square, to: Square) {
        val piece = from.piece ?: return
        val dx = to.x - from.x
        val dy = to.y - from.y
        val currentPieces = whitePieces.value + blackPieces.value

        var pieceJumped: Piece? = null
        if (abs(dx) >= 2) {
            val sx = dx / abs(dx)
            val sy = dy / abs(dy)
            var cx = from.x + sx
            var cy = from.y + sy
            while (cx != to.x) {
                val p = getPieceByPosition(cx, cy, currentPieces)
                if (p != null) { pieceJumped = p; break }
                cx += sx; cy += sy
            }
        }

        moveAndCapture(piece.id, to.x, to.y, pieceJumped?.id)

        val wasCapture = pieceJumped != null
        val movedPiece = piece.copy(x = to.x, y = to.y)
        val piecesAfterMove = currentPieces
            .filter { it.id != pieceJumped?.id }
            .map { if (it.id == piece.id) movedPiece else it }

        val pieceCanStillCapture = if (wasCapture) {
            getCaptureMoves(movedPiece, piecesAfterMove).isNotEmpty()
        } else false

        val nextTurn = if (pieceCanStillCapture) uiState.getTurn()
        else if (uiState.getTurn() == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE

        uiState.setTurn(nextTurn)
        uiState.setSelectedSquare(if (pieceCanStillCapture) to.copy(piece = movedPiece) else null)
        uiState.setAvailableMoves(if (pieceCanStillCapture) calculateMovesForPiece(movedPiece, piecesAfterMove) else emptyList())

        if (!pieceCanStillCapture) makeKing(movedPiece)
        checkWin(piecesAfterMove)
    }
    private suspend fun makeKing(piece: Piece) {
        if (
            (piece.color == PieceColor.WHITE && piece.y == 0) ||
            (piece.color == PieceColor.BLACK && piece.y == 7)
        ) promotePiece(piece.id)
    }

    private suspend fun checkWin(pieces: List<Piece>) {
        val currentTurn = uiState.getTurn()
        val otherColor = if (currentTurn == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE

        val filteredPieces = pieces.filter { it.color == currentTurn }
        if (filteredPieces.isEmpty()) {
            uiState.setWinner(otherColor)
            return
        }
        val canMove = filteredPieces.any { calculateMovesForPiece(it, pieces).isNotEmpty() }
        if (!canMove) {
            uiState.setWinner(otherColor)
        }
    }

    private fun getPieceByPosition(x: Int, y: Int, pieces: List<Piece>): Piece? =
        pieces.find { it.x == x && it.y == y && it.isAlive }
    private suspend fun moveAndCapture(moveId: Int, x: Int, y: Int, captureId: Int?) = repository.moveAndCapture(moveId, x, y, captureId)

    private suspend fun promotePiece(id: Int) = repository.promotePiece(id)
}
