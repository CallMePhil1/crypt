package com.github.callmephil1.crypt.ui.compose

import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun rememberPermission(permission: String): MutableState<Boolean> {
    val granted = ContextCompat.checkSelfPermission(LocalContext.current, permission) == PackageManager.PERMISSION_GRANTED
    return remember {
        mutableStateOf(granted)
    }
}