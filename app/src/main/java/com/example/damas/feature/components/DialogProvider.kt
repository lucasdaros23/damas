package com.example.damas.feature.components

import com.example.damas.domain.model.Dialog
import com.example.damas.resources.CheckersStrings
import javax.inject.Inject

class DialogProvider @Inject constructor(
    private val strings: CheckersStrings,
) {
    fun default(onCancel: () -> Unit) =
        Dialog(
            title = "title",
            message = "message",
            cancelText = "cancel",
            confirmText = "confirm",
            onCancel = onCancel,
            onConfirm = onCancel,
        )
    fun inexistentNavigation(onConfirm: () -> Unit) =
        Dialog(
            title = strings.getDialogInexistentNavigationTitle(),
            message = strings.getDialogInexistentNavigationMessage(),
            confirmText = strings.getDialogOk(),
            onConfirm = onConfirm
        )
    fun reset(onConfirm: () -> Unit, onCancel: () -> Unit) =
        Dialog(
            title = strings.getDialogResetNavigationTitle(),
            message = strings.getDialogResetNavigationMessage(),
            confirmText = strings.getDialogOk(),
            cancelText = strings.getDialogCancel(),
            onConfirm = onConfirm,
            onCancel = onCancel,
        )
}