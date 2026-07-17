package com.example.damas.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getResourceProvider(): ResourceProvider = ResourceProviderImpl(context = LocalContext.current)
