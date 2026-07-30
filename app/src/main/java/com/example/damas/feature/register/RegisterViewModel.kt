package com.example.damas.feature.register

import androidx.lifecycle.ViewModel
import com.example.damas.feature.components.TextFieldType
import com.example.damas.feature.components.dialog.DialogProvider
import com.example.damas.feature.register.RegisterUiEvent.ScreenEvent
import com.example.damas.navigation.Routes
import com.example.damas.resources.CheckersStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val uiState: RegisterUiState,
    val uiEvent: RegisterUiEvent,
    private val dialogProvider: DialogProvider,
    private val strings: CheckersStrings,
) : ViewModel() {
    fun setup() {
        with (receiver = strings) {
            uiState.update {
                it.copy(
                    userNameTextField = RegisterUiState.TextInputPresentation(
                        label = getRegisterUserNameTextFieldLabel(),
                    ),
                    emailTextField = RegisterUiState.TextInputPresentation(
                        label = getRegisterEmailTextFieldLabel(),
                        type = TextFieldType.EMAIL
                    ),
                    passwordTextField = RegisterUiState.TextInputPresentation(
                        label = getRegisterPasswordTextFieldLabel(),
                        type = TextFieldType.PASSWORD
                    ),
                    confirmPasswordTextField = RegisterUiState.TextInputPresentation(
                        label = getRegisterConfirmPasswordTextFieldLabel(),
                        type = TextFieldType.PASSWORD
                    ),
                    confirmButtonText = getRegisterConfirmButtonText(),
                )
            }
        }
    }

    init { setup() }

    fun onActionEvent(action: RegisterScreenAction) =
        action.fold(
            userNameTextFieldValueChangedAction = ::userNameTextFieldValueChangedAction,
            emailTextFieldValueChangedAction = ::emailTextFieldValueChangedAction,
            passwordTextFieldValueChangedAction = ::passwordTextFieldValueChangedAction,
            confirmPasswordTextFieldValueChangedAction = ::confirmPasswordTextFieldValueChangedAction,
            closeButtonClickedAction = ::closeButtonClickedAction,
            showPasswordButtonAction = ::showPasswordButtonAction,
            showConfirmPasswordButtonAction = ::showConfirmPasswordButtonAction,
            dialogConfirmAction = ::dialogConfirmAction,
            dialogCancelAction = ::dialogCancelAction,
            confirmButtonAction = ::confirmButtonAction,
        )

    private fun userNameTextFieldValueChangedAction(value: String) =
        uiState.update {
            it.copy(
                userNameTextField = uiState.presentation.value.userNameTextField.copy(value = value)
            )
        }

    private fun emailTextFieldValueChangedAction(value: String) =
        uiState.update {
            it.copy(
                emailTextField = uiState.presentation.value.emailTextField.copy(value = value)
            )
        }

    private fun passwordTextFieldValueChangedAction(value: String) =
        uiState.update {
            it.copy(
                passwordTextField = uiState.presentation.value.passwordTextField.copy(value = value)
            )
        }

    private fun confirmPasswordTextFieldValueChangedAction(value: String) =
        uiState.update {
            it.copy(
                confirmPasswordTextField = uiState.presentation.value.confirmPasswordTextField.copy(value = value)
            )
        }

    private fun closeButtonClickedAction() =
        uiEvent.send(event = ScreenEvent.NavigateBack)

    private fun showPasswordButtonAction() {}

    private fun showConfirmPasswordButtonAction() {}

    private fun dialogConfirmAction() {
        dialogCancelAction()
        //todo aqui vai ter o when
        // e dps tem q adicionar la no dialog type desse modulo as opcoes
    }

    private fun dialogCancelAction() =
        uiState.update { it.copy(activeDialog = null) }

    private fun confirmButtonAction() =
        uiEvent.send(event = ScreenEvent.NavigateAndClearBackStack(Routes.HOME))
    //todo DPS ADICIONAR OS BGL TUDO AQUI
}
