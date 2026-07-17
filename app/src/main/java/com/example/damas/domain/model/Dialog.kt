package com.example.damas.domain.model

data class Dialog(
    val title: String? = null,
    val message: String? = null,
    val confirmText: String? = null,
    val cancelText: String? = null,
    val onConfirm: () -> Unit = {},
    val onCancel: () -> Unit = {},
)
