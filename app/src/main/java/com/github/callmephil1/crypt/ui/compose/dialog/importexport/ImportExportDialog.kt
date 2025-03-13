package com.github.callmephil1.crypt.ui.compose.dialog.importexport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.callmephil1.crypt.ui.compose.PrimaryTextButton
import com.github.callmephil1.crypt.ui.compose.SecondaryTextButton
import org.koin.androidx.compose.koinViewModel

private enum class BackupMethods(val text: String) {
    FILE("File"),
    GOOGLE_DRIVE("Google Drive")
}

@Composable
private fun FileMethod(
    isImport: Boolean,
    onDismissRequest: () -> Unit,
    onPrimaryClick: () -> Unit
) {
    val buttonText = remember { if (isImport) "Import" else "Export" }
    var encryptKey by rememberSaveable { mutableStateOf("") }
    var fileName by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        TextField(
            value = fileName,
            onValueChange = { fileName = it },
            placeholder = { Text("File name") }
        )

        TextField(
            value = encryptKey,
            onValueChange = { encryptKey = it },
            placeholder = { Text("Encryption Key") }
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            SecondaryTextButton("Dismiss") { onDismissRequest() }
            PrimaryTextButton(
                text = buttonText,
                enabled = fileName.isNotBlank(),
                onClick = onPrimaryClick
            )
        }
    }
}

@Composable
private fun GoogleDriveMethod() {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportExportDialog(
    isImport: Boolean,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: ImportExportViewModel = koinViewModel()

    var backupMethod by remember { mutableStateOf(BackupMethods.FILE) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val title = remember { if (isImport) "Import Secrets" else "Export Secrets" }

    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = title)

                ExposedDropdownMenuBox(
                    expanded = dropdownExpanded,
                    onExpandedChange = { dropdownExpanded = it }
                ) {
                    TextField(
                        value = backupMethod.text,
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        BackupMethods.entries.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it.text) },
                                onClick = {
                                    backupMethod = it
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                when(backupMethod) {
                    BackupMethods.FILE -> {}
                    BackupMethods.GOOGLE_DRIVE -> {}
                }
            }
        }
    }
}