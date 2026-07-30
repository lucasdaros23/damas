package com.example.damas.feature.register

import com.example.damas.core.UiEvent
import com.example.damas.feature.register.RegisterUiEvent.ScreenEvent
import javax.inject.Inject

class RegisterUiEvent @Inject constructor() : UiEvent<ScreenEvent>() {
    sealed interface ScreenEvent {
        object NavigateBack : ScreenEvent
        data class NavigateAndClearBackStack(val route: String) : ScreenEvent
    }
}
