package com.example.damas.feature.login

import com.example.damas.core.EMPTY_STRING
import com.example.damas.domain.model.DialogModel
import com.example.damas.feature.components.TextFieldType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LoginUiState {
    val presentation = MutableStateFlow(value = Presentation())

    fun update(transform: (Presentation) -> Presentation) = presentation.update(transform)

    data class Presentation(
        val userTextField: TextInputPresentation = TextInputPresentation(),
        val passwordTextField: TextInputPresentation = TextInputPresentation(),
        val forgotPasswordText: String = EMPTY_STRING,
        val createAccountText: String = EMPTY_STRING,
        val confirmButtonText: String = EMPTY_STRING,
        val enterAsGuestText: String = EMPTY_STRING,
        val activeDialog: DialogModel? = null,
        val showPassword: Boolean = false,
    )

    data class TextInputPresentation(
        val value: String = EMPTY_STRING,
        val label: String = EMPTY_STRING,
        val type: TextFieldType = TextFieldType.DEFAULT,
    )
}