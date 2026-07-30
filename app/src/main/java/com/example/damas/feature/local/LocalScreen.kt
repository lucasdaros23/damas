package com.example.damas.feature.local

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.damas.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.damas.domain.model.DialogModel
import com.example.damas.domain.model.Piece
import com.example.damas.domain.model.Square
import com.example.damas.domain.model.enums.PieceColor
import com.example.damas.feature.components.GenericButton
import com.example.damas.feature.components.ReturnButton
import com.example.damas.feature.components.dialog.ScreenDialog
import com.example.damas.feature.local.LocalUiEvent.ScreenEvent
import com.example.damas.resources.Size
import com.example.damas.ui.theme.PieceBlack
import com.example.damas.ui.theme.PieceWhite
import com.example.damas.ui.theme.PurpleDetails
import com.example.damas.ui.theme.SquareBlack
import com.example.damas.ui.theme.SquareWhite


@Composable
fun LocalScreen(
    modifier: Modifier,
    viewModel: LocalViewModel,
    navigateBack: () -> Unit,
) {
    Screen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onActionEvent = viewModel::onActionEvent
    )
    EventConsumer(
        viewModel = viewModel,
        navigateBack = navigateBack
    )
}

@Composable
private fun Screen(
    modifier: Modifier,
    onActionEvent: (LocalScreenAction) -> Unit,
    uiState: LocalUiState
) {
    val presentation by uiState.presentation.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        ReturnButton(onClick = { onActionEvent(LocalScreenAction.CloseButtonClickedAction) } )
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    presentation.titleMessage,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.size(Size.sm1))
                val winner = presentation.winner
                PieceComponent(
                    Piece(
                        x = 8,
                        y = 8,
                        color = winner ?: presentation.turn
                    )
                )
            }
            Spacer(Modifier.size(Size.sm4))
            Board(
                board = presentation.board,
                availableMoves = presentation.availableMoves,
                selectedSquare = presentation.selectedSquare,
                squareClickedAction = { onActionEvent(LocalScreenAction.SquareClickedAction(it)) }
            )
            Spacer(Modifier.size(Size.md2))
            GenericButton(
                text = "reset",
                onClick = { onActionEvent(LocalScreenAction.ResetButtonClickedAction) }
            )
        }
        LocalScreenDialog(
            dialog = presentation.activeDialog,
            onActionEvent = onActionEvent
        )
    }
}

@Composable
private fun EventConsumer(
    viewModel: LocalViewModel,
    navigateBack: () -> Unit
) = LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
        when (event) {
            ScreenEvent.NavigateBack -> navigateBack()
        }
    }
}

@Composable
private fun Board(
    board: List<Square>,
    availableMoves: List<Square>,
    selectedSquare: Square?,
    squareClickedAction: (Square) -> Unit,
) {
    Column {
        board.chunked(8).forEach { row ->
            Row {
                row.forEach { square ->
                    val isAvailable = availableMoves.any { it.x == square.x && it.y == square.y }
                    val isSelected =
                        selectedSquare?.let { it.x == square.x && it.y == square.y } ?: false
                    Box(
                        modifier = Modifier
                            .size(Size.lg1)
                            .background(
                                color = if (square.isDark) SquareBlack else SquareWhite,
                                shape = RectangleShape
                            )
                            .border(
                                shape = RectangleShape,
                                width = Size.xs3,
                                color = if (isSelected) PurpleDetails else Color.Transparent
                            )
                            .clickable {
                                squareClickedAction(square)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        if (square.isOccupied) {
                            square.piece?.let { PieceComponent(it) }
                        } else if (isAvailable) {
                            AvailableMoveIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PieceComponent(piece: Piece) {
    Box(
        modifier = Modifier
            .size(Size.md3)
            .background(
                color = if (piece.color == PieceColor.WHITE) PieceWhite else PieceBlack,
                shape = CircleShape
            )
            .padding(Size.xs2)
    ) {
        if (piece.isKing) {
            Icon(
                painter = painterResource(R.drawable.crown_icon),
                "",
                tint = if (piece.color == PieceColor.WHITE) SquareBlack else SquareWhite
            )
        }
    }
}

@Composable
private fun AvailableMoveIndicator() {
    Box(
        modifier = Modifier
            .size(Size.sm2)
            .background(color = PurpleDetails, shape = CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    Screen(
        modifier = Modifier,
        { },
        LocalUiState().apply {
            update {
                it.copy(
                    board = buildList {
                        for (i in 0..63) {
                            add(
                                Square(
                                    x = i % 8,
                                    y = i / 8
                                )
                            )
                        }
                    },
                    resetButtonText = "Reset",
                    titleMessage = "Vez de "
                )
            }
        }
    )
}

@Composable
private fun LocalScreenDialog(
    dialog: DialogModel?,
    onActionEvent: (LocalScreenAction) -> Unit
) {
    dialog?.let {
        ScreenDialog(
            dialog = dialog,
            onConfirm = { onActionEvent(LocalScreenAction.DialogConfirmAction) },
            onCancel = { onActionEvent(LocalScreenAction.DialogCancelAction) },
        )
    }
}

@Preview
@Composable
private fun PieceComponentWhitePreview() {
    PieceComponent(
        Piece(0, 0, 0, color = PieceColor.WHITE)
    )
}

@Preview
@Composable
private fun PieceComponentBlackPreview() {
    PieceComponent(
        Piece(0, 0, 0, color = PieceColor.BLACK)
    )
}

@Preview
@Composable
private fun PieceComponentWhiteKingPreview() {
    PieceComponent(
        Piece(0, 0, 0, color = PieceColor.WHITE, isKing = true)
    )
}

@Preview
@Composable
private fun PieceComponentBlackKingPreview() {
    PieceComponent(
        Piece(0, 0, 0, color = PieceColor.BLACK, isKing = true)
    )
}