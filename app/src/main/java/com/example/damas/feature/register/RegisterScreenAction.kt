package com.example.damas.feature.register

sealed class RegisterScreenAction {
    data class UserNameTextFieldValueChangedAction(val value: String): RegisterScreenAction()
    data class EmailTextFieldValueChangedAction(val value: String): RegisterScreenAction()
    data class PasswordTextFieldValueChangedAction(val value: String): RegisterScreenAction()
    data class ConfirmPasswordTextFieldValueChangedAction(val value: String): RegisterScreenAction()
    object CloseButtonClickedAction: RegisterScreenAction()
    object ShowPasswordButtonAction: RegisterScreenAction()
    object ShowConfirmPasswordButtonAction: RegisterScreenAction()
    object DialogConfirmAction : RegisterScreenAction()
    object DialogCancelAction : RegisterScreenAction()
    object ConfirmButtonAction: RegisterScreenAction()
}

fun RegisterScreenAction.fold(
    userNameTextFieldValueChangedAction: (String) -> Unit,
    emailTextFieldValueChangedAction: (String) -> Unit,
    passwordTextFieldValueChangedAction: (String) -> Unit,
    confirmPasswordTextFieldValueChangedAction: (String) -> Unit,
    closeButtonClickedAction: () -> Unit,
    showPasswordButtonAction: () -> Unit,
    showConfirmPasswordButtonAction: () -> Unit,
    dialogConfirmAction: () -> Unit,
    dialogCancelAction: () -> Unit,
    confirmButtonAction: () -> Unit,
) {
    when (this){
        is RegisterScreenAction.UserNameTextFieldValueChangedAction -> userNameTextFieldValueChangedAction(value)
        is RegisterScreenAction.EmailTextFieldValueChangedAction -> emailTextFieldValueChangedAction(value)
        is RegisterScreenAction.PasswordTextFieldValueChangedAction -> passwordTextFieldValueChangedAction(value)
        is RegisterScreenAction.ConfirmPasswordTextFieldValueChangedAction -> confirmPasswordTextFieldValueChangedAction(value)
        RegisterScreenAction.CloseButtonClickedAction -> closeButtonClickedAction()
        RegisterScreenAction.ShowPasswordButtonAction -> showPasswordButtonAction()
        RegisterScreenAction.ShowConfirmPasswordButtonAction -> showConfirmPasswordButtonAction()
        RegisterScreenAction.DialogConfirmAction -> dialogConfirmAction()
        RegisterScreenAction.DialogCancelAction -> dialogCancelAction()
        RegisterScreenAction.ConfirmButtonAction -> confirmButtonAction()
    }
}
