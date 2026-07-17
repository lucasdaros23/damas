package com.example.damas.core

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(
        @StringRes resId: Int,
    ): String
}