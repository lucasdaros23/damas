package com.example.damas.feature.home

import com.example.damas.core.UiEvent
import com.example.damas.domain.model.Dialog
import com.example.damas.feature.home.HomeUiEvent.ScreenEvent
import javax.inject.Inject

class HomeUiEvent @Inject constructor() : UiEvent<ScreenEvent>(){
    sealed interface ScreenEvent {
        object NavigateBack : ScreenEvent
        data class Navigate(val route: String): ScreenEvent

        data class ShowDialog(
            val dialog: Dialog
        ) : ScreenEvent
    }

}