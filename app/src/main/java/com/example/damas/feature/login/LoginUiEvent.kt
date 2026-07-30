package com.example.damas.feature.login

import com.example.damas.core.UiEvent
import com.example.damas.feature.login.LoginUiEvent.ScreenEvent
import javax.inject.Inject

class LoginUiEvent @Inject constructor() : UiEvent<ScreenEvent>() {
    sealed interface ScreenEvent {
        data class Navigate(val route: String) : ScreenEvent
        data class NavigateAndClearBackStack(val route: String) : ScreenEvent
    }
}