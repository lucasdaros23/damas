package com.example.damas.feature.local

import com.example.damas.domain.model.DialogType

sealed interface LocalDialogType : DialogType {
    data object Reset : LocalDialogType
}
