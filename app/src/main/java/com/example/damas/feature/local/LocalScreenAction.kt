package com.example.damas.feature.local

import com.example.damas.domain.model.Square

sealed class LocalScreenAction {
    data class SquareClickedAction(val square: Square): LocalScreenAction()
    object ResetButtonClickedAction: LocalScreenAction()
    object CloseButtonClickedAction: LocalScreenAction()
    object DialogConfirmAction: LocalScreenAction()
    object DialogCancelAction: LocalScreenAction()
}

fun LocalScreenAction.fold(
    squareClickedAction: (Square) -> Unit,
    resetButtonClickedAction: () -> Unit,
    closeButtonClickedAction: () -> Unit,
    dialogConfirmAction: () -> Unit,
    dialogCancelAction: () -> Unit,
) {
    when (this) {
        LocalScreenAction.ResetButtonClickedAction -> resetButtonClickedAction()
        is LocalScreenAction.SquareClickedAction -> squareClickedAction(square)
        LocalScreenAction.CloseButtonClickedAction -> closeButtonClickedAction()
        LocalScreenAction.DialogConfirmAction -> dialogConfirmAction()
        LocalScreenAction.DialogCancelAction -> dialogCancelAction()
    }
}