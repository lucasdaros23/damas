package com.example.damas.feature.home

sealed class HomeScreenAction{
    data class ListButtonClickedAction(val index: Int) : HomeScreenAction()
    object CloseButtonClickedAction : HomeScreenAction()
    object DialogConfirmAction : HomeScreenAction()
    object DialogCancelAction : HomeScreenAction()
}

fun HomeScreenAction.fold(
    listButtonClickedAction: (Int) -> Unit,
    closeButtonClickedAction: () -> Unit,
    dialogConfirmAction: () -> Unit,
    dialogCancelAction: () -> Unit,
) {
    when (this) {
        is HomeScreenAction.ListButtonClickedAction -> listButtonClickedAction(index)
        HomeScreenAction.CloseButtonClickedAction -> closeButtonClickedAction()
        HomeScreenAction.DialogConfirmAction -> dialogConfirmAction()
        HomeScreenAction.DialogCancelAction -> dialogCancelAction()
    }
}
