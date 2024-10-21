package com.github.callmephil1.crypt.ui.compose.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.github.callmephil1.crypt.ui.compose.CryptScaffold
import com.github.callmephil1.crypt.ui.compose.PrimaryTextButton

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavToEntries: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigateToEntries) {
        if (uiState.navigateToEntries) {
            onNavToEntries()
        }
    }

    CryptScaffold { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
        ) {
            TextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChanged,
                placeholder = { Text("Enter password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.align(Alignment.Center)
            )

            PrimaryTextButton(
                text = "Authenticate",
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = viewModel::authenticate
            )
        }
    }
}