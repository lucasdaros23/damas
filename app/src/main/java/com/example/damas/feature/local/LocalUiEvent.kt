package com.example.damas.feature.local

import com.example.damas.core.UiEvent
import com.example.damas.domain.model.Dialog
import com.example.damas.feature.local.LocalUiEvent.ScreenEvent
import javax.inject.Inject

class LocalUiEvent @Inject constructor() : UiEvent<ScreenEvent>(){
    sealed interface ScreenEvent {
        object NavigateBack : ScreenEvent
        data class ShowDialog(
            val dialog: Dialog
        ) : ScreenEvent
    }
}