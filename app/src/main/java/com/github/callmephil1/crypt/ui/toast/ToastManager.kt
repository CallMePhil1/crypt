package com.github.callmephil1.crypt.ui.toast

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ToastMessage(
    val message: String = ""
)

interface ToastManager {
    val messages: StateFlow<ToastMessage>
    fun show(message: String)
}

class ToastManagerImpl : ToastManager {
    private val _messages = MutableStateFlow(ToastMessage())
    override val messages = _messages.asStateFlow()

    override fun show(message: String) {
        _messages.update { ToastMessage(message) }
    }
}