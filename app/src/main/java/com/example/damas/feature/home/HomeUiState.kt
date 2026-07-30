package com.example.damas.feature.home

import com.example.damas.domain.model.DialogModel
import com.example.damas.resources.CheckersStrings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class HomeUiState @Inject constructor(
    strings: CheckersStrings
) {
    val presentation = MutableStateFlow(value = Presentation())

    init{
        val list = listOf(
            strings.getHomeLocalGameButton(),
            strings.getHomeOnlineGameButton(),
            strings.getHomeComputerGameButton(),
        )
        update { it.copy(buttons = list) }
    }

    fun update(transform: (Presentation) -> Presentation) = presentation.update(transform)

    data class Presentation(
        val buttons: List<String> = emptyList(),
        val activeDialog: DialogModel? = null,
    )
}