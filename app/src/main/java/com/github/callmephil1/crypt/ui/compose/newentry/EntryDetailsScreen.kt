package com.github.callmephil1.crypt.ui.compose.newentry

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.callmephil1.crypt.R
import com.github.callmephil1.crypt.ui.compose.rememberPermission

@Composable
fun EntryLine(
    text: String,
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    onClicked: () -> Unit = {},
    onValueChanged: (String) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(true, onClick = onClicked)
        ) {
            TextField(
                value = text,
                onValueChange = onValueChanged,
                readOnly = true,
                singleLine = true,
                enabled = false,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                placeholder = {
                    Text(placeHolder)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun EntryDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: EntryDetailsViewModel,
    onNavToEntries: () -> Unit,
    onQrCodeButtonClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var cameraPermission by rememberPermission(Manifest.permission.CAMERA)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            cameraPermission = granted
        }
    )

    LaunchedEffect(true) {
        viewModel.updateState()
    }

    LaunchedEffect(uiState.navigateToEntries) {
        if (uiState.navigateToEntries) {
            onNavToEntries()
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = uiState.label,
                onValueChange = viewModel::onNameTextChanged,
                placeholder = {
                    Text("Name")
                },
                modifier = Modifier.fillMaxWidth()
            )

            EntryLine(
                text = uiState.secretText,
                placeHolder = "Secret",
                trailingIcon = {
                    Icon(
                        painterResource(R.drawable.baseline_refresh_24),
                        "",
                        modifier = Modifier.clickable { viewModel.generateRandomSecret() }
                    )
                },
                onClicked = {
                    viewModel.onSecretClicked()
                }
            )

            val leadingIcon: (@Composable () -> Unit)? = if (uiState.otpText.isBlank()) null else {
                {
                    CircularProgressIndicator(
                        progress = { uiState.otpRefreshCountDown.toFloat() },
                        strokeCap = StrokeCap.Round,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            EntryLine(
                text = uiState.otpText,
                placeHolder = "OTP",
                leadingIcon = leadingIcon,
                trailingIcon = {
                    Icon(
                        painterResource(R.drawable.outline_qr_code_2_add_24),
                        "",
                        modifier = Modifier.clickable {
                            if (!cameraPermission)
                                launcher.launch(Manifest.permission.CAMERA)
                            else
                                onQrCodeButtonClicked()
                        }
                    )
                },
                onClicked = viewModel::onOtpCodeClicked
            )
        }

        val enabled = uiState.secretText.isNotBlank() && uiState.label.isNotBlank()

        TextButton(
            onClick = {
                viewModel.onSavedClicked()
            },
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = contentColorFor(MaterialTheme.colorScheme.primaryContainer),
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            ),
            enabled = enabled,
            modifier = Modifier.fillMaxWidth().height(60.dp)
        ) {
            Text(
                text = "Save",
                fontSize = 24.sp,
                color = Color.White
            )
        }
    }
}

//@Preview
//@Composable
//fun NewEntryScreenPreview() {
////    KoinApplication(appModule(LocalContext.current)) {
////        val database = getKoin().get<CryptDatabase>(CryptDatabase::class, named("inmemory"))
////        NewEntryScreen(
////            viewModel = NewEntryViewModel(database),
////            modifier = Modifier.fillMaxSize(),
////        )
////    }
//}
//
//@Preview
//@Composable
//fun EntryLinePreview() {
//    EntryLine(
//        text = "tecx",
//        painter = painterResource(R.drawable.baseline_refresh_24),
//        placeHolder = "Placeholder",
//    )
//}