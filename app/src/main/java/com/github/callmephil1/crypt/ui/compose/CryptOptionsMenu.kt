package com.github.callmephil1.crypt.ui.compose

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CryptOptionsMenu(
    showOptionsMenu: Boolean = false,
    onDismissRequest: () -> Unit,
    onChangePasswordClicked: () -> Unit = {},
    onImportClicked: () -> Unit = {},
    onExportClicked: () -> Unit = {},
    onExitClicked: () -> Unit = {}
) {
    DropdownMenu(
        expanded = showOptionsMenu,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = { Text(text = "Change Password") },
            onClick = {
                onDismissRequest()
                onChangePasswordClicked()
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Import") },
            onClick = {
                onDismissRequest()
                onImportClicked()
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Export") },
            onClick = {
                onDismissRequest()
                onExportClicked()
            }
        )
        DropdownMenuItem(
            text = { Text(text = "Exit") },
            onClick = {
                onDismissRequest()
                onExitClicked()
            }
        )
    }
}