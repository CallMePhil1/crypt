package com.github.callmephil1.crypt.ui.compose.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.github.callmephil1.crypt.ui.compose.CryptScaffold
import com.github.callmephil1.crypt.ui.compose.PrimaryTextButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    CryptScaffold(showOptions = false) { innerPadding ->
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
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(60.dp),
                onClick = viewModel::authenticate
            )
        }
    }
}