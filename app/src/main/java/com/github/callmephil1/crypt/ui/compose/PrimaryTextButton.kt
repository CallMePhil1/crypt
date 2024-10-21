package com.github.callmephil1.crypt.ui.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryTextButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = contentColorFor(MaterialTheme.colorScheme.primaryContainer),
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        ),
        enabled = enabled,
        modifier = modifier.fillMaxWidth().height(60.dp)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = Color.White
        )
    }
}