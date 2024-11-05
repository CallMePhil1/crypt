package com.github.callmephil1.crypt.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SecondaryTextButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fontSize: TextUnit = 16.sp,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer),
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = contentColorFor(MaterialTheme.colorScheme.secondaryContainer),
       ),
        enabled = enabled,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = fontSize
        )
    }
}