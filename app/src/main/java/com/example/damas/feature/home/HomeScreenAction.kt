package com.example.damas.feature.home

import com.example.damas.feature.local.LocalScreenAction

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
