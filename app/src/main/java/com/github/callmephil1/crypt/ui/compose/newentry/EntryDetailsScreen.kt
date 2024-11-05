package com.github.callmephil1.crypt.ui.compose.newentry

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.callmephil1.crypt.R
import com.github.callmephil1.crypt.ui.compose.CryptScaffold
import com.github.callmephil1.crypt.ui.compose.PrimaryTextButton
import com.github.callmephil1.crypt.ui.compose.rememberPermission

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CryptTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: String,
    trailingIcon: @Composable (() -> Unit)?,
    onClick: () -> Unit
) {
    var editable by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.combinedClickable(onLongClick = { editable = true }, onClick = onClick)
    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            enabled = editable,
            placeholder = { Text(text = placeHolder) },
            trailingIcon = trailingIcon,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun EntryDetailsScreen(
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

    CryptScaffold { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
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

                CryptTextField(
                    text = uiState.secretText,
                    onValueChange = viewModel::onSecretValueChanged,
                    placeHolder = "Secret",
                    trailingIcon = {
                        Icon(
                            painterResource(R.drawable.baseline_refresh_24),
                            "",
                            modifier = Modifier.clickable { viewModel.generateRandomSecret() }
                        )
                    },
                    onClick = viewModel::onSecretClick,
                    modifier = Modifier.fillMaxWidth()
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

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(true, onClick = viewModel::onOtpCodeClick)
                ) {
                    TextField(
                        value = uiState.otpText,
                        onValueChange = {},
                        readOnly = true,
                        singleLine = true,
                        enabled = false,
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
                        placeholder = {
                            Text("OTP")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            val enabled = uiState.secretText.isNotBlank() && uiState.label.isNotBlank()

            PrimaryTextButton(
                text = "Save",
                enabled = enabled,
                onClick = viewModel::onSavedClick,
                modifier = Modifier.fillMaxWidth().height(60.dp)
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