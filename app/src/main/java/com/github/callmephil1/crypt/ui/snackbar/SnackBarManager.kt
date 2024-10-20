package com.github.callmephil1.crypt.ui.snackbar

import androidx.compose.material3.SnackbarHostState

interface SnackbarManager {
    val snackbarHostState: SnackbarHostState
    suspend fun showSnackbar(message: String)
}

class SnackbarManagerImpl : SnackbarManager {
    override val snackbarHostState = SnackbarHostState()

    override suspend fun showSnackbar(message: String) {
        snackbarHostState.showSnackbar(message)
    }
}