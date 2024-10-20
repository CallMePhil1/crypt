package com.github.callmephil1.crypt.ui.compose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.callmephil1.crypt.data.EntryItem

@Composable
fun EntryList(
    entries: List<EntryItem>,
    onEditButtonClicked: (Int) -> Unit,
    onOtpClicked: (Int) -> Unit,
    onOtpLongClicked: (Int) -> Unit,
    onSecretClicked: (Int) -> Unit,
    onSecretLongClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        itemsIndexed(
            entries,
            key = { _, item ->
                item.id
            }
        ) { _, item ->
            ListItem(
                item.name,
                item.otpText,
                item.otpCountDown,
                item.otpRefreshCountDown,
                item.secretCountDown,
                onEditButtonClicked = { onEditButtonClicked(item.id) },
                onOtpClicked = { onOtpClicked(item.id) },
                onOtpLongClicked = { onOtpLongClicked(item.id) },
                onSecretClicked = { onSecretClicked(item.id) },
                onSecretLongClicked = { onSecretLongClicked(item.id) },
                modifier = Modifier
                    .height(100.dp)
            )
        }
    }
}

@Preview
@Composable
private fun EntryListPreview() {
    EntryList(
        listOf(
            EntryItem(0,"Google", 0.5f, 0.3f, 0.5f, "123456", "123456"),
            EntryItem(1, "Google", 0.5f, 0.3f, 0.5f,"123456", "123456"),
            EntryItem(2, "Google", 0.5f, 0.3f, 0.5f,"123456", "123456")
        ),
        onEditButtonClicked = {},
        onOtpClicked = { Log.d("", "Otp clicked") },
        onOtpLongClicked = { Log.d("", "Otp long clicked") },
        onSecretClicked = { Log.d("", "Secret clicked") },
        onSecretLongClicked = { Log.d("", "Secret long clicked") }
    )
}