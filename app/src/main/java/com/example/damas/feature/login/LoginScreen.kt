package com.example.damas.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.damas.domain.model.DialogModel
import com.example.damas.feature.components.DefaultTextField
import com.example.damas.feature.components.GenericButton
import com.example.damas.feature.components.GenericTextButton
import com.example.damas.feature.components.PasswordTextField
import com.example.damas.feature.components.dialog.ScreenDialog
import com.example.damas.feature.login.LoginUiEvent.ScreenEvent
import com.example.damas.resources.FontSize
import com.example.damas.resources.Size

@Composable
fun LoginScreen(
    modifier: Modifier,
    viewModel: LoginViewModel,
    navigate: (String) -> Unit,
    navigateAndClearBackStack: (String) -> Unit,
) {
    Screen(
        modifier = modifier,
        onActionEvent = viewModel::onActionEvent,
        uiState = viewModel.uiState,
    )
    EventConsumer(
        viewModel = viewModel,
        navigate = navigate,
        navigateAndClearBackStack = navigateAndClearBackStack,
    )
}

@Composable
private fun EventConsumer(
    viewModel: LoginViewModel,
    navigate: (String) -> Unit,
    navigateAndClearBackStack: (String) -> Unit,
) = LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
        when (event) {
            is ScreenEvent.Navigate -> navigate(event.route)
            is ScreenEvent.NavigateAndClearBackStack -> navigateAndClearBackStack(event.route)
        }
    }
}

@Composable
private fun Screen(
    modifier: Modifier,
    onActionEvent: (LoginScreenAction) -> Unit,
    uiState: LoginUiState
) {
    val presentation by uiState.presentation.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        with(receiver = presentation.userTextField) {
            DefaultTextField(
                value = value,
                onValueChanged = { onActionEvent(LoginScreenAction.UserTextFieldValueChangedAction(value = it)) },
                placeholder = label
            )
        }
        with(receiver = presentation.passwordTextField) {
            PasswordTextField(
                value = value,
                onValueChanged = { onActionEvent(LoginScreenAction.PasswordTextFieldValueChangedAction(value = it)) },
                placeholder = label
            )
        }
        Row(
            modifier = Modifier
                .width(Size.xxl2),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GenericTextButton(
                text = presentation.forgotPasswordText,
                onClick = { onActionEvent(LoginScreenAction.ForgotPasswordButtonAction) },
            )
            GenericTextButton(
                text = presentation.createAccountText,
                onClick = { onActionEvent(LoginScreenAction.CreateAccountButtonAction) },
            )
        }
        GenericButton(
            text = presentation.enterAsGuestText,
            onClick = { onActionEvent(LoginScreenAction.EnterAsGuestButtonAction) },
            fontSize = FontSize.md1,
        )
        Spacer(Modifier.size(Size.xs2))
        GenericButton(
            text = presentation.confirmButtonText,
            onClick = { onActionEvent(LoginScreenAction.ConfirmButtonAction) },
            fontSize = FontSize.md1,
        )
    }
    LoginScreenDialog(
        dialog = presentation.activeDialog,
        onActionEvent = onActionEvent
    )
}

@Composable
private fun LoginScreenDialog(
    dialog: DialogModel?,
    onActionEvent: (LoginScreenAction) -> Unit
) {
    dialog?.let {
        ScreenDialog(
            dialog = dialog,
            onConfirm = { onActionEvent(LoginScreenAction.DialogConfirmAction) },
            onCancel = { onActionEvent(LoginScreenAction.DialogCancelAction) },
        )
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    Screen(
        modifier = Modifier,
        uiState = LoginUiState().apply {

        },
        onActionEvent = {}
    )
}
