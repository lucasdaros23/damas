package com.example.damas.feature.register

import com.example.damas.core.EMPTY_STRING
import com.example.damas.domain.model.DialogModel
import com.example.damas.feature.components.TextFieldType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class RegisterUiState {
    val presentation = MutableStateFlow(value = Presentation())

    fun update(transform: (Presentation) -> Presentation) = presentation.update(transform)

    data class Presentation(
        val userNameTextField: TextInputPresentation = TextInputPresentation(),
        val emailTextField: TextInputPresentation = TextInputPresentation(),
        val passwordTextField: TextInputPresentation = TextInputPresentation(),
        val confirmPasswordTextField: TextInputPresentation = TextInputPresentation(),
        val confirmButtonText: String = EMPTY_STRING,
        val activeDialog: DialogModel? = null,
    )

    data class TextInputPresentation(
        val value: String = EMPTY_STRING,
        val label: String = EMPTY_STRING,
        val type: TextFieldType = TextFieldType.DEFAULT,
    )
}
