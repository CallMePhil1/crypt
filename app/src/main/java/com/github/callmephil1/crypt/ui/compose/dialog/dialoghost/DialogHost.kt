package com.github.callmephil1.crypt.ui.compose.dialog.dialoghost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.github.callmephil1.crypt.ui.compose.dialog.changepassword.ChangePasswordDialog
import com.github.callmephil1.crypt.ui.compose.dialog.importexport.ImportExportDialog
import org.koin.compose.koinInject

@Composable
fun DialogHost() {
    val dialogController = koinInject<DialogController>()
    val dialog by dialogController.dialogs.collectAsState()

    when(dialog) {
        Dialogs.CHANGE_PASSWORD -> {
            ChangePasswordDialog(
                onDismissRequest = { dialogController.dismiss() }
            )
        }
        Dialogs.EXPORT -> ImportExportDialog(false, onDismissRequest = dialogController::dismiss)
        Dialogs.IMPORT -> ImportExportDialog(true, onDismissRequest = dialogController::dismiss)
        Dialogs.NONE -> {}
    }
}