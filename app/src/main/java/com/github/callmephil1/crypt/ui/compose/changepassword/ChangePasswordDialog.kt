package com.github.callmephil1.crypt.ui.compose.changepassword

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.github.callmephil1.crypt.ui.compose.PrimaryTextButton
import com.github.callmephil1.crypt.ui.compose.SecondaryTextButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChangePasswordDialog(
    onDismissRequest: () -> Unit
) {
    val viewModel: ChangePasswordViewModel = koinViewModel()
    var confirmPasswordText: String by remember { mutableStateOf("") }
    var newPasswordText: String by remember { mutableStateOf("") }
    var oldPasswordText: String by remember { mutableStateOf("") }

    val submitEnabled = oldPasswordText.isNotBlank() && newPasswordText.isNotBlank() && confirmPasswordText == newPasswordText

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Change password", fontSize = 24.sp)
                TextField(
                    oldPasswordText,
                    onValueChange = { oldPasswordText = it },
                    placeholder = { Text("Old password") }
                )
                TextField(
                    newPasswordText,
                    onValueChange = { newPasswordText = it },
                    placeholder = { Text("New password") }
                )
                TextField(
                    confirmPasswordText,
                    onValueChange = { confirmPasswordText = it },
                    placeholder = { Text("Confirm password") }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SecondaryTextButton("Dismiss") { onDismissRequest() }
                    PrimaryTextButton(
                        text = "Change",
                        enabled = submitEnabled,
                        onClick = { viewModel.changePassword(oldPasswordText, newPasswordText) }
                    )
                }
            }
        }
    }
}