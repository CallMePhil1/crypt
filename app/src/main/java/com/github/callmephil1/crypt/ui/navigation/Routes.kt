package com.github.callmephil1.crypt.ui.navigation

import androidx.navigation.NavController

object Routes {
    const val ENTRIES = "entries"
    const val ENTRY_DETAIL = "entry_detail?is_new={id}"
    const val QR_SCANNER = "qr_scanner"
}

fun NavController.navigateToEntries() {
    this.navigate(Routes.ENTRIES)
}

fun NavController.navigateToEntryDetail(id: Int = 0) {
    val endpoint = Routes.ENTRY_DETAIL.replace("{id}", id.toString())
    this.navigate(endpoint)
}

fun NavController.navigateToQRScanner() {
    this.navigate(Routes.QR_SCANNER)
}