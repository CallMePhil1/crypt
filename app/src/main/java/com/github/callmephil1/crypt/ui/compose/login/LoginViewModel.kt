package com.github.callmephil1.crypt.ui.compose.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.callmephil1.crypt.data.DatabaseManager
import com.github.callmephil1.crypt.ui.snackbar.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val navigateToEntries: Boolean = false,
    val password: String = ""
)

class LoginViewModel(
    private val databaseManager: DatabaseManager,
    private val snackbarManager: SnackbarManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun authenticate() {
        val result = databaseManager.init(_uiState.value.password)

        when {
            result.isSuccess -> {
                _uiState.update {
                    it.copy(navigateToEntries = true)
                }
            }
            result.isFailure -> {
                viewModelScope.launch {
                    snackbarManager.showSnackbar("Failed authentication: ${result.exceptionOrNull()}")
                }
            }
        }
    }

    fun onPasswordChanged(value: String) {
        _uiState.update {
            it.copy(password = value)
        }
    }
}