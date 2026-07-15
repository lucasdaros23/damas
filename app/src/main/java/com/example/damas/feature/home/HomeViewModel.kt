package com.example.damas.feature.home

import androidx.lifecycle.ViewModel
import com.example.damas.feature.home.HomeUiEvent.ScreenEvent
import com.example.damas.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val uiState: HomeUiState,
    val uiEvent: HomeUiEvent,
) : ViewModel(){
    fun onActionEvent(action: HomeScreenAction) {
        action.fold(
            listButtonClickedAction = ::listButtonClickedAction,
            closeButtonClickedAction = ::closeButtonClickedAction
        )
    }
    private fun listButtonClickedAction(index: Int){
        val route = when(index){
            0 -> Routes.LOCAL
            1 -> Routes.ONLINE
            2 -> Routes.COMPUTER
            else -> ""
        }
        uiEvent.send(event = ScreenEvent.Navigate(route))
    }
    private fun closeButtonClickedAction(){
        uiEvent.send(event = ScreenEvent.NavigateBack)
    }
}