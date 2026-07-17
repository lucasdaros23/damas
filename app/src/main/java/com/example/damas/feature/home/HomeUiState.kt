package com.example.damas.feature.home

import com.example.damas.domain.model.Dialog
import com.example.damas.feature.local.LocalUiState.Presentation
import com.example.damas.resources.CheckersStrings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class HomeUiState @Inject constructor(
    private val strings: CheckersStrings
) {
    val presentation = MutableStateFlow(value = Presentation())

    init{
        setButtons()
    }

    fun setButtons() {
        val list = listOf(
            strings.getHomeLocalGameButton(),
            strings.getHomeOnlineGameButton(),
            strings.getHomeComputerGameButton(),
        )
        presentation.update { it.copy(buttons = list) }
    }

    fun showDialog(dialog: Dialog?) {
        presentation.update { it.copy(activeDialog = dialog) }
    }

    data class Presentation(
        val buttons: List<String> = emptyList(),
        val activeDialog: Dialog? = null,
    )
}