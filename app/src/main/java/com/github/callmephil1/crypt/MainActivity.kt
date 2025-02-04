package com.github.callmephil1.crypt

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.callmephil1.crypt.di.appModule
import com.github.callmephil1.crypt.ui.compose.dialog.dialoghost.DialogHost
import com.github.callmephil1.crypt.ui.compose.entries.EntriesScreen
import com.github.callmephil1.crypt.ui.compose.login.LoginScreen
import com.github.callmephil1.crypt.ui.compose.newentry.EntryDetailsScreen
import com.github.callmephil1.crypt.ui.compose.qrscan.QrScanScreen
import com.github.callmephil1.crypt.ui.navigation.Routes
import com.github.callmephil1.crypt.ui.navigation.navigateToEntries
import com.github.callmephil1.crypt.ui.navigation.navigateToEntryDetail
import com.github.callmephil1.crypt.ui.navigation.navigateToQRScanner
import com.github.callmephil1.crypt.ui.toast.ToastManager
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        System.loadLibrary("sqlcipher")

        setContent {
            KoinApplication(appModule(this@MainActivity.applicationContext)) {
                val context = LocalContext.current
                val navController = rememberNavController()
                val toastManager = koinInject<ToastManager>()
                val toastMessage by toastManager.messages.collectAsState()

                LaunchedEffect(toastMessage) {
                    if (toastMessage.message.isNotBlank())
                        Toast.makeText(context, toastMessage.message, Toast.LENGTH_SHORT).show()
                }

                DialogHost()

                NavHost(
                    navController = navController,
                    startDestination = Routes.AUTHENTICATE
                ) {
                    composable(Routes.AUTHENTICATE) {
                        LoginScreen(
                            viewModel = koinViewModel(),
                            onNavToEntries = navController::navigateToEntries
                        )
                    }

                    composable(Routes.ENTRIES) {
                        EntriesScreen(
                            viewModel = koinViewModel(),
                            onNavToEntryDetails = navController::navigateToEntryDetail,
                        )
                    }

                    composable(
                        Routes.ENTRY_DETAIL,
                        listOf(navArgument("id") {
                            this.type = NavType.IntType
                            this.defaultValue = 0
                        })
                    ) {
                        EntryDetailsScreen(
                            viewModel = koinViewModel(),
                            onNavToEntries = { navController.navigateToEntries() },
                            onQrCodeButtonClicked = { navController.navigateToQRScanner() },
                        )
                    }

                    composable(Routes.QR_SCANNER) {
                        QrScanScreen(
                            viewModel = koinViewModel(),
                            onDismissClicked = { navController.popBackStack() },
                            onNavToNewEntry = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}