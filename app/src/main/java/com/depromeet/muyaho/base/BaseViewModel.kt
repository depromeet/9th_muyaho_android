package com.depromeet.muyaho.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow

abstract class BaseViewModel<A : Action> : ViewModel() {
    protected val actionSender = BroadcastChannel<A>(Channel.BUFFERED)
    val actionReceiver = actionSender.asFlow()
}