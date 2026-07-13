package com.example.damas.domain.model

data class Dialog(
    val message: String,
    val confirmText: String? = null,
    val cancelText: String? = null,
    val onConfirm: () -> Unit,
)
