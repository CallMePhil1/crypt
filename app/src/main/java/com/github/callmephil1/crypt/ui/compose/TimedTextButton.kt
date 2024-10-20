package com.github.callmephil1.crypt.ui.compose

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimedTextButton(
    modifier: Modifier = Modifier,
    progress: () -> Float,
    progressBarColor: Color,
    progressBarThickness: Int = 5,
    contentPaddingValues: PaddingValues = PaddingValues(),
    enabled: Boolean = true,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    val columnModifier = if (enabled) modifier.combinedClickable(onLongClick = onLongClick, onClick = onClick) else modifier
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = columnModifier

    ) {
        Row(modifier = Modifier.padding(contentPaddingValues)) {
            content()
        }

        LinearProgressIndicator(
            progress = progress,
            color = progressBarColor,
            modifier = Modifier
                .height(progressBarThickness.dp)
                .alpha(if (progress() > 0) 1f else 0f)
        )
    }
}

@Preview
@Composable
private fun TimedTextButtonPreview() {
    TimedTextButton(
        progress = { 0.5f },
        progressBarColor = Color.Red,
        progressBarThickness = 5,
        onClick = { Log.d("", "Clicked") },
        onLongClick = { Log.d("", "Long Click") }
    ) {
        Text("Testing")
    }
}