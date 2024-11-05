package com.github.callmephil1.crypt.ui.compose.dialog.importexport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.callmephil1.crypt.ui.compose.PrimaryTextButton
import com.github.callmephil1.crypt.ui.compose.SecondaryTextButton
import com.github.callmephil1.crypt.ui.compose.dialog.changepassword.ChangePasswordViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ImportExportDialog(
    isImport: Boolean,
    onDismissRequest: () -> Unit
) {
    val viewModel: ChangePasswordViewModel = koinViewModel()
    val buttonText = remember { if (isImport) "Import" else "Export" }
    val title = remember { if (isImport) "Import Secrets" else "Export Secrets" }
    var fileName by rememberSaveable { mutableStateOf("") }
    var encryptKey by rememberSaveable { mutableStateOf("") }

    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = title)

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
                        onClick = {  }
                    )
                }
            }
        }
    }
}