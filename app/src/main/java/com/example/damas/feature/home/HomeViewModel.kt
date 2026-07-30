package com.example.damas.feature.home

import androidx.lifecycle.ViewModel
import com.example.damas.feature.components.dialog.DialogProvider
import com.example.damas.feature.home.HomeUiEvent.ScreenEvent
import com.example.damas.navigation.Routes
import com.example.damas.resources.CheckersStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val uiState: HomeUiState,
    val uiEvent: HomeUiEvent,
    private val dialogProvider: DialogProvider,
    private val strings: CheckersStrings,
) : ViewModel(){
    fun onActionEvent(action: HomeScreenAction) =
        action.fold(
            listButtonClickedAction = ::listButtonClickedAction,
            closeButtonClickedAction = ::closeButtonClickedAction,
            dialogConfirmAction = ::dialogCancelAction,
            dialogCancelAction = ::dialogCancelAction,
        )

    private fun listButtonClickedAction(index: Int){
        when(index){
            0 -> uiEvent.send(ScreenEvent.Navigate(Routes.LOCAL))
            1, 2 -> uiState.update {
                it.copy(
                    activeDialog = dialogProvider.inexistentNavigation().copy(message = strings.getHomeDialogInexistentNavigationMessage())
                )
            }
        }
    }

    private fun closeButtonClickedAction(){
        uiEvent.send(event = ScreenEvent.NavigateBack)
    }

    private fun dialogConfirmAction() {
        // todo DPS VAI TER COISA AQUI
    }

    private fun dialogCancelAction() =
        uiState.update{ it.copy(activeDialog = null) }
}