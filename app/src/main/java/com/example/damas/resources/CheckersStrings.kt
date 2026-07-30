package com.example.damas.resources

import com.example.damas.R
import com.example.damas.core.ResourceProvider
import javax.inject.Inject

class CheckersStrings @Inject constructor(
    private val resourceProvider: ResourceProvider,
) {
    // LOGIN
    fun getLoginUserTextFieldLabel() = resourceProvider.getString(R.string.login_user_text_field_label)
    fun getLoginPasswordTextFieldLabel() = resourceProvider.getString(R.string.login_password_text_field_label)
    fun getLoginForgotPasswordText() = resourceProvider.getString(R.string.login_forgot_password_text)
    fun getLoginCreateAccountText() = resourceProvider.getString(R.string.login_create_account_text)
    fun getLoginEnterAsGuestText() = resourceProvider.getString(R.string.login_enter_as_guest_text)
    fun getLoginConfirmButtonText() = resourceProvider.getString(R.string.login_confirm_button_text)
    fun getLoginDialogInexistentNavigationMessage() = resourceProvider.getString(R.string.login_dialog_inexistent_navigation_message)


    // HOME
    fun getHomeLocalGameButton() = resourceProvider.getString(R.string.home_local_game_button)
    fun getHomeOnlineGameButton() = resourceProvider.getString(R.string.home_online_game_button)
    fun getHomeComputerGameButton() = resourceProvider.getString(R.string.home_computer_game_button)
    fun getHomeDialogInexistentNavigationMessage() = resourceProvider.getString(R.string.home_dialog_inexistent_navigation_message)

    // LOCAL
    fun getLocalTitleTurn() = resourceProvider.getString(R.string.local_title_turn)
    fun getLocalTitleWinner() = resourceProvider.getString(R.string.local_title_winner)
    fun getLocalResetButton() = resourceProvider.getString(R.string.local_reset_button)


    // DIALOGS
    fun getDialogOk() = resourceProvider.getString(R.string.dialog_ok)
    fun getDialogCancel() = resourceProvider.getString(R.string.dialog_cancel)

    fun getDialogInexistentNavigationTitle() = resourceProvider.getString(R.string.dialog_inexistent_navigation_title)

    fun getDialogResetNavigationTitle() = resourceProvider.getString(R.string.dialog_reset_title)
    fun getDialogResetNavigationMessage() = resourceProvider.getString(R.string.dialog_reset_message)
}
