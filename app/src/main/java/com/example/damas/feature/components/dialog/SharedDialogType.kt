package com.example.damas.feature.components.dialog

import com.example.damas.domain.model.DialogType

sealed interface SharedDialogType : DialogType {
    data object Default : SharedDialogType
    data object InexistentNavigation : SharedDialogType
}