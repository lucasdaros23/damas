package com.example.damas.feature.home

import androidx.lifecycle.ViewModel
import com.example.damas.feature.components.DialogProvider
import com.example.damas.feature.home.HomeUiEvent.ScreenEvent
import com.example.damas.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val uiState: HomeUiState,
    val uiEvent: HomeUiEvent,
    private val dialogProvider: DialogProvider,
) : ViewModel(){
    fun onActionEvent(action: HomeScreenAction) {
        action.fold(
            listButtonClickedAction = ::listButtonClickedAction,
            closeButtonClickedAction = ::closeButtonClickedAction
        )
    }

    private fun listButtonClickedAction(index: Int){
        fun closeDialog() = uiState.update { it.copy(activeDialog = null) }
        when(index){
            0 -> uiEvent.send(ScreenEvent.Navigate(Routes.LOCAL))
            1, 2 -> uiState.update {
                it.copy(
                    activeDialog = dialogProvider.inexistentNavigation(
                        onConfirm = { closeDialog() }
                    )
                )
            }
        }
    }

    private fun closeButtonClickedAction(){
        uiEvent.send(event = ScreenEvent.NavigateBack)
    }
}