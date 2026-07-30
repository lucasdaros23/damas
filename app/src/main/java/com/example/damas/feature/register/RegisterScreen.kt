package com.example.damas.feature.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.damas.feature.components.PasswordTextField
import com.example.damas.feature.components.ReturnButton
import com.example.damas.feature.components.dialog.ScreenDialog
import com.example.damas.feature.register.RegisterUiEvent.ScreenEvent
import com.example.damas.resources.FontSize

@Composable
fun RegisterScreen(
    modifier: Modifier,
    viewModel: RegisterViewModel,
    navigateBack: () -> Unit,
    navigateAndClearBackStack: (String) -> Unit,
) {
    Screen(
        modifier = modifier,
        onActionEvent = viewModel::onActionEvent,
        uiState = viewModel.uiState,
    )
    EventConsumer(
        viewModel = viewModel,
        navigateBack = navigateBack,
        navigateAndClearBackStack = navigateAndClearBackStack,
    )
}

@Composable
private fun EventConsumer(
    viewModel: RegisterViewModel,
    navigateBack: () -> Unit,
    navigateAndClearBackStack: (String) -> Unit,
) = LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
        when (event) {
            is ScreenEvent.NavigateBack -> navigateBack()
            is ScreenEvent.NavigateAndClearBackStack -> navigateAndClearBackStack(event.route)
        }
    }
}

@Composable
private fun Screen(
    modifier: Modifier,
    onActionEvent: (RegisterScreenAction) -> Unit,
    uiState: RegisterUiState
) {
    val presentation by uiState.presentation.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        ReturnButton(onClick = { onActionEvent(RegisterScreenAction.CloseButtonClickedAction) })
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            with(receiver = presentation.userNameTextField) {
                DefaultTextField(
                    value = value,
                    onValueChanged = {
                        onActionEvent(
                            RegisterScreenAction.UserNameTextFieldValueChangedAction(
                                value = it
                            )
                        )
                    },
                    placeholder = label
                )
            }
            with(receiver = presentation.emailTextField) {
                DefaultTextField(
                    value = value,
                    onValueChanged = {
                        onActionEvent(
                            RegisterScreenAction.EmailTextFieldValueChangedAction(
                                value = it
                            )
                        )
                    },
                    placeholder = label
                )
            }
            with(receiver = presentation.passwordTextField) {
                PasswordTextField(
                    value = value,
                    onValueChanged = {
                        onActionEvent(
                            RegisterScreenAction.PasswordTextFieldValueChangedAction(
                                value = it
                            )
                        )
                    },
                    placeholder = label
                )
            }
            with(receiver = presentation.passwordTextField) {
                PasswordTextField(
                    value = value,
                    onValueChanged = {
                        onActionEvent(
                            RegisterScreenAction.ConfirmPasswordTextFieldValueChangedAction(
                                value = it
                            )
                        )
                    },
                    placeholder = label
                )
            }
            GenericButton(
                text = presentation.confirmButtonText,
                onClick = { onActionEvent(RegisterScreenAction.ConfirmButtonAction) },
                fontSize = FontSize.md1,
            )
        }
    }
    RegisterScreenDialog(
        dialog = presentation.activeDialog,
        onActionEvent = onActionEvent
    )
}

@Composable
private fun RegisterScreenDialog(
    dialog: DialogModel?,
    onActionEvent: (RegisterScreenAction) -> Unit
) {
    dialog?.let {
        ScreenDialog(
            dialog = dialog,
            onConfirm = { onActionEvent(RegisterScreenAction.DialogConfirmAction) },
            onCancel = { onActionEvent(RegisterScreenAction.DialogCancelAction) },
        )
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    Screen(
        modifier = Modifier,
        uiState = RegisterUiState().apply {

        },
        onActionEvent = {}
    )
}