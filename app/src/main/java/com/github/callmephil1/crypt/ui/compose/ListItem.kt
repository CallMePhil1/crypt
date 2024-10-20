package com.github.callmephil1.crypt.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListItem(
    name: String,
    otpText: String,
    otpCountDown: Float,
    otpRefreshCountDown: Float,
    secretCountDown: Float,
    onEditButtonClicked: () -> Unit,
    onOtpClicked: () -> Unit,
    onOtpLongClicked: () -> Unit,
    onSecretClicked: () -> Unit,
    onSecretLongClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults
            .elevatedCardElevation(
                defaultElevation = 3.dp,
                disabledElevation = 1.dp
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .padding(start = 20.dp, end = 8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "",
                    modifier = Modifier.size(48.dp)
                )
                Text(text = name, fontSize = 10.sp)
            }

            TimedTextButton(
                progress = { secretCountDown },
                progressBarColor = MaterialTheme.colorScheme.tertiary,
                contentPaddingValues = PaddingValues(4.dp),
                onClick = onSecretClicked,
                onLongClick = onSecretLongClicked,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(0.5f)
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.size(3.dp))
                Text(
                    text = "Secret",
                    fontSize = 20.sp,
                    softWrap = false
                )
            }

            TimedTextButton(
                progress = { otpCountDown },
                progressBarColor = MaterialTheme.colorScheme.tertiary,
                contentPaddingValues = PaddingValues(4.dp),
                enabled = otpText.isNotBlank(),
                onClick = onOtpClicked,
                onLongClick = onOtpLongClicked,
                modifier = Modifier.weight(0.4f)
            ) {
                if (otpText.isNotBlank()) {
                    CircularProgressIndicator(
                        progress = { otpRefreshCountDown },
                        color = MaterialTheme.colorScheme.tertiary,
                        strokeCap = StrokeCap.Round,
                        strokeWidth = 2.dp,
                        trackColor = Color.Transparent,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.size(3.dp))
                    Text(
                        text = otpText,
                        fontSize = 20.sp
                    )
                }
            }
            IconButton(
                onClick = onEditButtonClicked,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ListItemPreview() {
    ListItem(
        "Google",
        "123456",
        0.5f ,
        0.5f,
        0.5f,
        {},
        {},
        {},
        {},
        {}
    )
}