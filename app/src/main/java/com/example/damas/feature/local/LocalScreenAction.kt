package com.example.damas.feature.local

import com.example.damas.domain.model.Square

sealed class LocalScreenAction {
    data class SquareClickedAction(val square: Square): LocalScreenAction()
    object ResetButtonClickedAction: LocalScreenAction()
    object CloseButtonClickedAction: LocalScreenAction()
    object ResetButtonAlertConfirmAction: LocalScreenAction()
    object ResetButtonAlertCancelAction: LocalScreenAction()
}

fun LocalScreenAction.fold(
    squareClickedAction: (Square) -> Unit,
    resetButtonClickedAction: () -> Unit,
    closeButtonClickedAction: () -> Unit,
    resetButtonAlertConfirmAction: () -> Unit,
    resetButtonAlertCancelAction: () -> Unit,
) {
    when (this) {
        LocalScreenAction.ResetButtonClickedAction -> resetButtonClickedAction()
        is LocalScreenAction.SquareClickedAction -> squareClickedAction(square)
        LocalScreenAction.CloseButtonClickedAction -> closeButtonClickedAction()
        LocalScreenAction.ResetButtonAlertConfirmAction -> resetButtonAlertConfirmAction()
        LocalScreenAction.ResetButtonAlertCancelAction -> resetButtonAlertCancelAction()
    }
}