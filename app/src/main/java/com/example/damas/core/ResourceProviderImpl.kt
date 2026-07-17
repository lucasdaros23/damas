package com.example.damas.core

import android.content.Context
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    val context: Context,
) : ResourceProvider {
    override fun getString(resId: Int) = context.getString(resId)
}