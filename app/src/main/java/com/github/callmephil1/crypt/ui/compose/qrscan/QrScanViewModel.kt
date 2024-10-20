package com.github.callmephil1.crypt.ui.compose.qrscan

import android.net.Uri
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import com.github.callmephil1.crypt.data.EntryDetailsManager
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class QrScanUiState(
    val showCamera: Boolean = true
)

class QrScanViewModel(
    private val barcodeScanner: BarcodeScanner,
    private val entryDetailsManager: EntryDetailsManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(QrScanUiState())
    val uiState = _uiState.asStateFlow()

    private val _navToNewEntry = MutableStateFlow(false)
    val navToNewEntry = _navToNewEntry.asStateFlow()

    fun onDismissClicked() {
        _uiState.update { it.copy(showCamera = false) }
    }

    @OptIn(ExperimentalGetImage::class)
    fun processBarcode(imageProxy: ImageProxy) {
        val inputImage = InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)
        barcodeScanner.process(inputImage)
            .addOnSuccessListener {
                if (it.isNotEmpty()) {
                    val barcode = it[0]
                    if (barcode.rawValue?.startsWith("otpauth") == true) {
                        val uri = Uri.parse(barcode.rawValue)

                        when(val qrCodeSecret = uri.getQueryParameter("secret")) {
                            null -> return@addOnSuccessListener
                            else -> {
                                entryDetailsManager.apply {
                                    label = uri.lastPathSegment ?: ""
                                    this.qrCodeSecret = qrCodeSecret
                                    issuer = uri.getQueryParameter("issuer") ?: ""
                                }
                                _navToNewEntry.update { true }
                            }
                        }
                    }
                }
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}