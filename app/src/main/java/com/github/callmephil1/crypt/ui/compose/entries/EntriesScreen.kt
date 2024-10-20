package com.github.callmephil1.crypt.ui.compose.entries

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.callmephil1.crypt.R
import com.github.callmephil1.crypt.data.CryptDatabase
import com.github.callmephil1.crypt.data.EntryDetailsManager
import com.github.callmephil1.crypt.data.clipboard.NoopClipboard
import com.github.callmephil1.crypt.di.appModule
import com.github.callmephil1.crypt.ui.compose.EntryList
import com.github.callmephil1.crypt.ui.snackbar.SnackbarManagerImpl
import com.github.callmephil1.crypt.ui.toast.ToastManagerImpl
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.core.qualifier.named

@Composable
fun EntriesScreen(
    modifier: Modifier = Modifier,
    viewModel: EntriesScreenViewModel,
    navigateToEntryDetails: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
    ) {
        EntryList(
            uiState.entries,
            onEditButtonClicked = {
                scope.launch {
                    viewModel.entryDetailsManager.loadEntry(it)
                    navigateToEntryDetails(it)
                }
            },
            viewModel::onOtpClicked,
            viewModel::onOtpLongClicked,
            viewModel::onSecretClicked,
            viewModel::onSecretLongClicked
        )

        FloatingActionButton(
            onClick = { navigateToEntryDetails(0) },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(36.dp)
                .size(dimensionResource(R.dimen.action_button_size))
        ) {
            Icon(
                Icons.Filled.Add,
                ""
            )
        }
    }
}

@Preview
@Composable
private fun ListScreenPreview() {
    KoinApplication(appModule(LocalContext.current)) {
        val database = getKoin().get<CryptDatabase>(CryptDatabase::class, named("inmemory"))
        EntriesScreen(
            viewModel = EntriesScreenViewModel(
                NoopClipboard(),
                database,
                EntryDetailsManager(database),
                SnackbarManagerImpl(),
                ToastManagerImpl()
            ),
            navigateToEntryDetails = {}
        )
    }
}