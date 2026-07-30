package com.example.damas.feature.components.dialog

import com.example.damas.domain.model.DialogModel
import com.example.damas.feature.local.LocalDialogType
import com.example.damas.resources.CheckersStrings
import javax.inject.Inject

class DialogProvider @Inject constructor(
    private val strings: CheckersStrings,
) {
    fun default() =
        DialogModel(
            type = SharedDialogType.Default,
            title = "title",
            message = "message",
            cancelText = "cancel",
            confirmText = "confirm",
        )
    fun inexistentNavigation() =
        DialogModel(
            type = SharedDialogType.InexistentNavigation,
            title = strings.getDialogInexistentNavigationTitle(),
            confirmText = strings.getDialogOk(),
        )
    fun reset() =
        DialogModel(
            type = LocalDialogType.Reset,
            title = strings.getDialogResetNavigationTitle(),
            message = strings.getDialogResetNavigationMessage(),
            confirmText = strings.getDialogOk(),
            cancelText = strings.getDialogCancel(),
        )
}