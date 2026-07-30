package com.example.damas.feature.login

import androidx.lifecycle.ViewModel
import com.example.damas.feature.components.TextFieldType
import com.example.damas.feature.components.dialog.DialogProvider
import com.example.damas.navigation.Routes
import com.example.damas.feature.login.LoginUiEvent.ScreenEvent
import com.example.damas.resources.CheckersStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val uiState: LoginUiState,
    val uiEvent: LoginUiEvent,
    private val dialogProvider: DialogProvider,
    private val strings: CheckersStrings,
) : ViewModel() {
    fun setup() {
        with(receiver = strings) {
            uiState.update {
                it.copy(
                    userTextField = LoginUiState.TextInputPresentation(
                        label = getLoginUserTextFieldLabel(),
                        type = TextFieldType.EMAIL
                    ),
                    passwordTextField = LoginUiState.TextInputPresentation(
                        label = getLoginPasswordTextFieldLabel(),
                        type = TextFieldType.PASSWORD
                    ),
                    confirmButtonText = getLoginConfirmButtonText(),
                    forgotPasswordText = getLoginForgotPasswordText(),
                    createAccountText = getLoginCreateAccountText(),
                    enterAsGuestText = getLoginEnterAsGuestText()
                )
            }
        }
    }

    init { setup() }

    fun onActionEvent(action: LoginScreenAction) =
        action.fold(
            userTextFieldValueChangedAction = ::userTextFieldValueChangedAction,
            passwordTextFieldValueChangedAction = ::passwordTextFieldValueChangedAction,
            confirmButtonAction = ::confirmButtonAction,
            createAccountButtonAction = ::createAccountButtonAction,
            enterAsGuestButtonAction = ::enterAsGuestButtonAction,
            forgotPasswordButtonAction = ::forgotPasswordButtonAction,
            showPasswordButtonAction = ::showPasswordButtonAction,
            dialogConfirmAction = ::dialogConfirmAction,
            dialogCancelAction = ::dialogCancelAction,
        )

    private fun userTextFieldValueChangedAction(value: String) =
        uiState.update {
            it.copy(
                userTextField = uiState.presentation.value.userTextField.copy(value = value)
            )
        }

    private fun passwordTextFieldValueChangedAction(value: String) =
        uiState.update {
            it.copy(
                passwordTextField = uiState.presentation.value.passwordTextField.copy(value = value)
            )
        }

    private fun confirmButtonAction() {
        //todo CHAMAR O REPOSITORY DPS DE ADICIONAR O FIREBASE
        // e redirecionar
        showInexistentNavigationDialog()
    }

    private fun createAccountButtonAction() =
        uiEvent.send(event = ScreenEvent.Navigate(Routes.REGISTER))



    private fun enterAsGuestButtonAction() =
        uiEvent.send(event = ScreenEvent.NavigateAndClearBackStack(Routes.HOME))

    private fun forgotPasswordButtonAction() = showInexistentNavigationDialog()

    private fun showPasswordButtonAction() =
        uiState.update { it.copy(showPassword = uiState.presentation.value.showPassword.not()) }

    private fun dialogConfirmAction() {
        dialogCancelAction()
        //todo aqui vai ter o when
        // e dps tem q adicionar la no dialog type desse modulo as opcoes
    }

    private fun dialogCancelAction() =
        uiState.update { it.copy(activeDialog = null) }

    private fun showInexistentNavigationDialog() =
        uiState.update {
            it.copy(
                activeDialog = dialogProvider.inexistentNavigation().copy(message = strings.getLoginDialogInexistentNavigationMessage())
            )
        }
}
