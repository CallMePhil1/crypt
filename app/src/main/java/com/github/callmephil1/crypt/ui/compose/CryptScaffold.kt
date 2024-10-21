package com.github.callmephil1.crypt.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.github.callmephil1.crypt.ui.snackbar.SnackbarManager
import com.github.callmephil1.crypt.ui.theme.CryptTheme
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptScaffold(
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val navController = rememberNavController()
    val snackbarManager = koinInject<SnackbarManager>()

    CryptTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crypt", modifier = Modifier.padding(horizontal = 16.dp)) },
                    navigationIcon = navigationIcon,
                    actions = {
                        Icon(
                            Icons.Filled.MoreVert,
                            "",
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarManager.snackbarHostState) },
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}