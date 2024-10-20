package com.github.callmephil1.crypt.ui.compose.entries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.callmephil1.crypt.data.CryptDatabase
import com.github.callmephil1.crypt.data.EntryDetailsManager
import com.github.callmephil1.crypt.data.EntryItem
import com.github.callmephil1.crypt.data.TOTP
import com.github.callmephil1.crypt.data.clipboard.Clipboard
import com.github.callmephil1.crypt.ui.snackbar.SnackbarManager
import com.github.callmephil1.crypt.ui.toast.ToastManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EntriesScreenUiState(
    val entries: List<EntryItem> = listOf()
)

class EntriesScreenViewModel(
    private val clipboard: Clipboard,
    private val cryptDatabase: CryptDatabase,
    val entryDetailsManager: EntryDetailsManager,
    private val snackbarManager: SnackbarManager,
    private val toastManager: ToastManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(EntriesScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            while(true) {
                val otpRefreshCountDown = TOTP.progressToRefresh().toFloat()

                val newEntries = _uiState.value.entries.map {

                    val secretCountDown = if (it.secretCountDown > 0) it.secretCountDown - 0.02f else 0f
                    val otpCountDown = if (it.otpCountDown > 0) it.otpCountDown - 0.02f else 0f

                    it.copy(
                        otpCountDown = otpCountDown,
                        otpRefreshCountDown = if (it.otpSecret.isBlank()) 0f else otpRefreshCountDown,
                        otpText = TOTP.generateCode(it.otpSecret),
                        secretCountDown = secretCountDown
                    )
                }
                _uiState.update { it.copy(entries = newEntries) }
                delay(100)
            }
        }
        viewModelScope.launch {
            val secretEntries = cryptDatabase.secretEntryDao().getAll()
            val entries = secretEntries.map {
                EntryItem(
                    id = it.id,
                    name = it.label,
                    otpSecret = it.qrCodeSecret,
                    otpText = ""
                )
            }
            _uiState.update {
                it.copy(entries = entries)
            }
        }
    }

    fun onOtpClicked(entryId: Int) {
        _uiState.value.entries
            .firstOrNull { it.id == entryId }
            ?.let { it.otpCountDown = 1f }
    }

    fun onOtpLongClicked(entryId: Int) {
        _uiState.value.entries
            .firstOrNull { it.id == entryId }
            ?.let {
                viewModelScope.launch {
                    clipboard.setString("otp", it.otpText)
                    toastManager.show("Copied OTP")
                }
            }
    }

    fun onSecretClicked(entryId: Int) {
        _uiState.value.entries
            .firstOrNull { it.id == entryId }
            ?.let { it.secretCountDown = 1f }
    }

    fun onSecretLongClicked(entryId: Int) {
        _uiState.value.entries
            .firstOrNull { it.id == entryId }
            ?.let {
                viewModelScope.launch {
                    val secretEntry = cryptDatabase.secretEntryDao().get(entryId)!!
                    clipboard.setString("secret", secretEntry.secret)
                    toastManager.show("Copied Secret")
                }
            }
    }
}