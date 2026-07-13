package com.example.damas.core

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.receiveAsFlow

open class UiEvent<T> {
    private val eventsChannel = Channel<T>(capacity = Channel.BUFFERED)

    val eventsFlow: Flow<T> = eventsChannel.receiveAsFlow()

    fun send(event: T) {
        eventsChannel.trySend(event)
    }
    suspend fun collect(collector: FlowCollector<T>) {
        eventsFlow.collect(collector = collector)
    }

}