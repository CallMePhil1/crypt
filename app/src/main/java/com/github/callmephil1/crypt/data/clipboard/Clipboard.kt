package com.github.callmephil1.crypt.data.clipboard

import android.content.ClipData
import android.content.ClipboardManager

interface Clipboard {
    fun setString(label: String, value: String): Unit
}

class AndroidClipboard(
    private val clipboardManager: ClipboardManager
) : Clipboard {

    override fun setString(label: String, value: String) {
        clipboardManager.setPrimaryClip(ClipData.newPlainText(label, value))
    }
}

class NoopClipboard : Clipboard {
    override fun setString(label: String, value: String) {
        return
    }
}