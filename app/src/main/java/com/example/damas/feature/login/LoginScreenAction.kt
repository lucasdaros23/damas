package com.example.damas.feature.login

sealed class LoginScreenAction {
    data class UserTextFieldValueChangedAction(val value: String): LoginScreenAction()
    data class PasswordTextFieldValueChangedAction(val value: String): LoginScreenAction()
    object ConfirmButtonAction: LoginScreenAction()
    object CreateAccountButtonAction: LoginScreenAction()
    object EnterAsGuestButtonAction: LoginScreenAction()
    object ForgotPasswordButtonAction: LoginScreenAction()
    object ShowPasswordButtonAction: LoginScreenAction()
    object DialogConfirmAction : LoginScreenAction()
    object DialogCancelAction : LoginScreenAction()
}

fun LoginScreenAction.fold(
    userTextFieldValueChangedAction: (String) -> Unit,
    passwordTextFieldValueChangedAction: (String) -> Unit,
    confirmButtonAction: () -> Unit,
    createAccountButtonAction: () -> Unit,
    enterAsGuestButtonAction: () -> Unit,
    forgotPasswordButtonAction: () -> Unit,
    showPasswordButtonAction: () -> Unit,
    dialogConfirmAction: () -> Unit,
    dialogCancelAction: () -> Unit,
) {
    when (this) {
        is LoginScreenAction.UserTextFieldValueChangedAction -> userTextFieldValueChangedAction(value)
        is LoginScreenAction.PasswordTextFieldValueChangedAction -> passwordTextFieldValueChangedAction(value)
        LoginScreenAction.ConfirmButtonAction -> confirmButtonAction()
        LoginScreenAction.CreateAccountButtonAction -> createAccountButtonAction()
        LoginScreenAction.EnterAsGuestButtonAction -> enterAsGuestButtonAction()
        LoginScreenAction.ShowPasswordButtonAction -> showPasswordButtonAction()
        LoginScreenAction.ForgotPasswordButtonAction -> forgotPasswordButtonAction()
        LoginScreenAction.DialogConfirmAction -> dialogConfirmAction()
        LoginScreenAction.DialogCancelAction -> dialogCancelAction()
    }
}