package com.example.damas.domain.model

import com.example.damas.feature.components.dialog.SharedDialogType

data class DialogModel(
    val type: DialogType = SharedDialogType.Default,
    val title: String? = null,
    val message: String? = null,
    val confirmText: String? = null,
    val cancelText: String? = null,
)

interface DialogType
