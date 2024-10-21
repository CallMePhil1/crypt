package com.github.callmephil1.crypt

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.callmephil1.crypt.di.appModule
import com.github.callmephil1.crypt.ui.compose.entries.EntriesScreen
import com.github.callmephil1.crypt.ui.compose.login.LoginScreen
import com.github.callmephil1.crypt.ui.compose.newentry.EntryDetailsScreen
import com.github.callmephil1.crypt.ui.compose.qrscan.QrScanScreen
import com.github.callmephil1.crypt.ui.navigation.Routes
import com.github.callmephil1.crypt.ui.navigation.navigateToEntries
import com.github.callmephil1.crypt.ui.navigation.navigateToEntryDetail
import com.github.callmephil1.crypt.ui.navigation.navigateToQRScanner
import com.github.callmephil1.crypt.ui.snackbar.SnackbarManager
import com.github.callmephil1.crypt.ui.theme.CryptTheme
import com.github.callmephil1.crypt.ui.toast.ToastManager
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        System.loadLibrary("sqlcipher")

//        val databaseFile = getDatabasePath(BuildConfig.DATABASE_FILE)
//        SQLiteDatabase.openOrCreateDatabase(
//            databaseFile,
//            "test",
//            null,
//            null
//        )

        enableEdgeToEdge()
        setContent {
            KoinApplication(appModule(this@MainActivity.applicationContext)) {
                val context = LocalContext.current
                val navController = rememberNavController()
                val snackbarManager = koinInject<SnackbarManager>()
                val toastManager = koinInject<ToastManager>()
                val toastMessage by toastManager.messages.collectAsState()

                LaunchedEffect(toastMessage) {
                    if (toastMessage.message.isNotBlank())
                        Toast.makeText(context, toastMessage.message, Toast.LENGTH_SHORT).show()
                }

                CryptTheme {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Crypt", modifier = Modifier.padding(horizontal = 16.dp)) },
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
                        modifier = Modifier.fillMaxSize()

                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Routes.AUTHENTICATE,
                            modifier = Modifier.padding(innerPadding).padding(12.dp)
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
                                    modifier = Modifier.fillMaxSize()
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
                                    modifier = Modifier.fillMaxSize()
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
    }
}