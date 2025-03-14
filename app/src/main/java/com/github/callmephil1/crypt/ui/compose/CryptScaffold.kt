package com.github.callmephil1.crypt.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.github.callmephil1.crypt.ui.compose.dialog.dialoghost.DialogController
import com.github.callmephil1.crypt.ui.compose.dialog.dialoghost.Dialogs
import com.github.callmephil1.crypt.ui.navigation.NavigationHelper
import com.github.callmephil1.crypt.ui.snackbar.SnackbarManager
import com.github.callmephil1.crypt.ui.theme.CryptTheme
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptScaffold(
    showOptions: Boolean = true,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val dialogController = koinInject<DialogController>()
    val navController = rememberNavController()
    var showOptionsMenu by remember { mutableStateOf(false) }
    val snackbarManager = koinInject<SnackbarManager>()



    CryptTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crypt", modifier = Modifier.padding(horizontal = 16.dp)) },
                    navigationIcon = navigationIcon,
                    actions = {
                        if (showOptions) {
                            Icon(
                                Icons.Filled.MoreVert,
                                "",
                                modifier = Modifier.padding(horizontal = 8.dp)
                                    .clickable { showOptionsMenu = true }
                            )
                            CryptOptionsMenu(
                                showOptionsMenu = showOptionsMenu,
                                onDismissRequest = { showOptionsMenu = false },
                                onChangePasswordClicked = { dialogController.show(Dialogs.CHANGE_PASSWORD) },
                                onImportClicked = { dialogController.show(Dialogs.IMPORT) },
                                onExportClicked = { dialogController.show(Dialogs.EXPORT) }
                            )
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarManager.snackbarHostState) },
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}