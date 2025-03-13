package com.github.callmephil1.crypt.ui.compose.dialog.dialoghost

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class Dialogs {
    CHANGE_PASSWORD,
    EXPORT,
    IMPORT,
    NONE
}

class DialogController {

    private val _dialogs = MutableStateFlow(Dialogs.NONE)
    val dialogs = _dialogs.asStateFlow()

    fun dismiss() {
        _dialogs.update { Dialogs.NONE }
    }

    fun show(dialog: Dialogs) {
        _dialogs.update { dialog }
    }
}