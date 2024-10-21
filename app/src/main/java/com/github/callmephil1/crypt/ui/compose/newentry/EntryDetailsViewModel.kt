package com.github.callmephil1.crypt.ui.compose.newentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.callmephil1.crypt.data.EntryDetailsManager
import com.github.callmephil1.crypt.data.TOTP
import com.github.callmephil1.crypt.data.clipboard.Clipboard
import com.github.callmephil1.crypt.ui.toast.ToastManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class DialogState {
    NONE,
    OPEN,
    CONFIRMED
}

data class EntryDetailsUiState(
    val id: Int = 0,
    val label: String = "",
    val navigateToEntries: Boolean = false,
    val otpText: String = "",
    val otpRefreshCountDown: Double = 0.0,
    val secretText: String = "",
    val showChangeSecretDialog: DialogState = DialogState.NONE,
    val showChangeQRCodeDialog: DialogState = DialogState.NONE,
    val showCamera: Boolean = false
)

class EntryDetailsViewModel(
    private val clipboard: Clipboard,
    private val entryDetailsManager: EntryDetailsManager,
    private val toastManager: ToastManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(EntryDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            while(true) {
                if (entryDetailsManager.qrCodeSecret.isNotBlank()) {
                    _uiState.update { it.copy(otpRefreshCountDown = TOTP.progressToRefresh()) }
                    generateOtpCode()
                }
                delay(100)
            }
        }

        if (entryDetailsManager.id != 0) {
            _uiState.update {
                it.copy(
                    id = entryDetailsManager.id,
                    label = entryDetailsManager.label,
                    secretText = entryDetailsManager.secret
                )
            }
        }
    }

    fun generateRandomSecret() {
        _uiState.update {
            it.copy(secretText = (0..30)
                .map { validCharacters[Random.nextInt(validCharacters.size)] }
                .joinToString("")
            )
        }
    }

    private fun generateOtpCode() {
        _uiState.update { it.copy(otpText = TOTP.generateCode(entryDetailsManager.qrCodeSecret)) }
    }

    fun onNameTextChanged(value: String) {
        _uiState.update { it.copy(label = value) }
    }

    fun onOtpCodeClicked() {
        if (uiState.value.otpText.isNotBlank()) {
            viewModelScope.launch {
                clipboard.setString("OTP", uiState.value.secretText)
                toastManager.show("Copied OTP")
            }
        }
    }

    fun onSavedClicked() {
        viewModelScope.launch {
            entryDetailsManager.label = _uiState.value.label
            entryDetailsManager.secret = _uiState.value.secretText
            entryDetailsManager.saveEntry()
            entryDetailsManager.clearEntry()
            _uiState.update { it.copy(navigateToEntries = true) }
        }
    }

    fun onSecretClicked() {
        if (uiState.value.secretText.isNotBlank()) {
            viewModelScope.launch {
                clipboard.setString("Secret", uiState.value.secretText)
                toastManager.show("Copied secret")
            }
        }
    }

    fun updateState() {
        _uiState.update {
            it.copy(
                label = entryDetailsManager.label
            )
        }
    }

    companion object {
        val validCharacters = ('!'..'~').toList()
    }
}