package com.example.damas.feature.register

import com.example.damas.domain.model.DialogType

sealed interface RegisterDialogType : DialogType {
    data object Success : RegisterDialogType
    // todo ADICIONAR OS CASOS DE ERRO DE register ex.
    // preencha todos os campos
    // algum campo preenchido incorretamente
    // erro de servidor
    // usuario ja existente com esse email/nome
}
