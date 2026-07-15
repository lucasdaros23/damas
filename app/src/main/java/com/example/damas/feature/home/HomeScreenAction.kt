package com.example.damas.feature.home

sealed class HomeScreenAction{
    data class ListButtonClickedAction(val index: Int): HomeScreenAction()
    object CloseButtonClickedAction: HomeScreenAction()
}

fun HomeScreenAction.fold(
    listButtonClickedAction: (Int) -> Unit,
    closeButtonClickedAction: () -> Unit
) {
    when (this) {
        is HomeScreenAction.ListButtonClickedAction -> listButtonClickedAction(index)
        HomeScreenAction.CloseButtonClickedAction -> closeButtonClickedAction()
    }
}
