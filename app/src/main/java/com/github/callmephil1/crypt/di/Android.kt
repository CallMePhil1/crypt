package com.github.callmephil1.crypt.di

import android.content.ClipboardManager
import android.content.Context
import androidx.camera.core.ImageAnalysis
import com.github.callmephil1.crypt.data.clipboard.AndroidClipboard
import com.github.callmephil1.crypt.data.clipboard.Clipboard
import com.github.callmephil1.crypt.data.clipboard.NoopClipboard
import com.github.callmephil1.crypt.ui.snackbar.SnackbarManager
import com.github.callmephil1.crypt.ui.snackbar.SnackbarManagerImpl
import com.github.callmephil1.crypt.ui.toast.ToastManager
import com.github.callmephil1.crypt.ui.toast.ToastManagerImpl
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val androidServices = module {

    single<Clipboard>(named("impl")) {
        val androidClipboard = get<Context>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        AndroidClipboard(androidClipboard)
    }
    single<Clipboard>(named("noop")) { NoopClipboard() }

    single<BarcodeScanner> {
        BarcodeScanning.getClient(
            BarcodeScannerOptions
                .Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
        )
    }

    single<ImageAnalysis> {
        ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
    }

    singleOf<SnackbarManager>(::SnackbarManagerImpl)
    singleOf<ToastManager>(::ToastManagerImpl)
}