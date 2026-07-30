package com.example.damas.feature.login

import com.example.damas.domain.model.DialogType

sealed interface LoginDialogType : DialogType{
    data object Success : LoginDialogType
    // todo ADICIONAR OS CASOS DE ERRO DE LOGIN ex.
    // usuario ou senha incorretos
    // erro de servidor
    // preencha todos os campos

}