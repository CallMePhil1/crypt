package com.github.callmephil1.crypt

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.github.callmephil1.crypt.ui.compose.dialog.dialoghost.DialogHost
import com.github.callmephil1.crypt.ui.compose.entries.EntriesScreen
import com.github.callmephil1.crypt.ui.compose.login.LoginScreen
import com.github.callmephil1.crypt.ui.compose.newentry.EntryDetailsScreen
import com.github.callmephil1.crypt.ui.compose.qrscan.QrScanScreen
import com.github.callmephil1.crypt.ui.navigation.Destination
import com.github.callmephil1.crypt.ui.navigation.NavigationHelper
import com.github.callmephil1.crypt.ui.toast.ToastManager
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        System.loadLibrary("sqlcipher")

        setContent {
            KoinAndroidContext {
                val context = LocalContext.current
                val navController = rememberNavController()
                val navigationHelper = koinInject<NavigationHelper>()
                val navigationState by navigationHelper.navigationFlow.collectAsState()
                val toastManager = koinInject<ToastManager>()
                val toastMessage by toastManager.messages.collectAsState()

                LaunchedEffect(toastMessage) {
                    if (toastMessage.message.isNotBlank())
                        Toast.makeText(context, toastMessage.message, Toast.LENGTH_SHORT).show()
                }

                LaunchedEffect(navigationState) {
                    if (navigationState == null) {
                        return@LaunchedEffect
                    }
                    navController.navigate(navigationState!!)
                }

                DialogHost()

                NavHost(
                    navController = navController,
                    startDestination = Destination.Authentication
                ) {
                    composable<Destination.Authentication> {
                        LoginScreen()
                    }

                    composable<Destination.Entries> {
                        EntriesScreen(
                            onNavToEntryDetails = { navigationHelper.navigate(Destination.EntryDetails(it)) },
                        )
                    }

                    composable<Destination.EntryDetails> { backStackEntry ->
                        val details = backStackEntry.toRoute<Destination.EntryDetails>()
                        EntryDetailsScreen(
                            onDismiss = { navController.popBackStack() },
                            onQrCodeButtonClicked = { navigationHelper.navigate(Destination.QrScanner) },
                        )
                    }

                    composable<Destination.QrScanner> {
                        QrScanScreen(
                            onDismissClicked = { navController.popBackStack() },
                            onNavToNewEntry = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}