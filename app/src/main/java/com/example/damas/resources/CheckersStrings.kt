package com.example.damas.resources

import com.example.damas.R
import com.example.damas.core.ResourceProvider
import javax.inject.Inject

class CheckersStrings @Inject constructor(
    private val resourceProvider: ResourceProvider,
) {
    // HOME
    fun getHomeLocalGameButton() = resourceProvider.getString(R.string.home_local_game_button)
    fun getHomeOnlineGameButton() = resourceProvider.getString(R.string.home_online_game_button)
    fun getHomeComputerGameButton() = resourceProvider.getString(R.string.home_computer_game_button)

    // LOCAL
    fun getLocalTitleTurn() = resourceProvider.getString(R.string.local_title_turn)
    fun getLocalTitleWinner() = resourceProvider.getString(R.string.local_title_winner)
    fun getLocalResetButton() = resourceProvider.getString(R.string.local_reset_button)


    // DIALOGS
    fun getDialogOk() = resourceProvider.getString(R.string.dialog_ok)
    fun getDialogCancel() = resourceProvider.getString(R.string.dialog_cancel)

    fun getDialogInexistentNavigationTitle() = resourceProvider.getString(R.string.dialog_inexistent_navigation_title)
    fun getDialogInexistentNavigationMessage() = resourceProvider.getString(R.string.dialog_inexistent_navigation_message)

    fun getDialogResetNavigationTitle() = resourceProvider.getString(R.string.dialog_reset_title)
    fun getDialogResetNavigationMessage() = resourceProvider.getString(R.string.dialog_reset_message)

}