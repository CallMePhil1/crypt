package com.github.callmephil1.crypt.ui.theme

import android.os.Build
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

//private val LightColorScheme = lightColorScheme(
//    primary = SaltBox,
//    primaryContainer = SaltBox,
//    onPrimary = Color.White,
//    secondary = Color.Black,
//    secondaryContainer = Sprout,
//    tertiary = Color.Blue,
//    tertiaryContainer = Color.Black,
//    surface = surface,
//    background = surface,
//    onBackground = Color(0xFFEF9A9A),
//    onSurface = Color.Black
//)

private val atomicBlueberry = lightColorScheme(
    primary = SaltBox,
    primaryContainer = FlushOrange,
    onPrimaryContainer = Color(0xFFE2ECFF),
    onPrimary = Color.White,
    secondary = Color.Black,
    secondaryContainer = FlushOrange,
    onSecondaryContainer = Chambray,
    surface = Chambray,
    background = Color(0xFFE2ECFF),
    onBackground = Color(0xFFEF9A9A),
)

private val savanna = lightColorScheme(
    background = Color(0xFFD9E1EA),
    primaryContainer = Color(0xFFED8D8D),
    onPrimaryContainer = Color(0xFF393232),
    secondaryContainer = Color(0xFFEEDCDC),
    surface = Color(0xFFCFB997),
    surfaceContainer = Color(0xFFE8E6E6),
    onSurface = Color(0xFF393232),
    tertiary = Color(0xFF6f8aa1)
)

private val avocado = lightColorScheme(
    background = Color(0xFFFFFBF1),
    primaryContainer = Color(0xff685369),
    onPrimaryContainer = Color(0xfff0f0f0),
    secondaryContainer = Color(0xFFE5DAE5),
    onSecondaryContainer = Color(0xfff0f0f0),
    surface = Color(0xFFC6D8AF),
    surfaceContainer = Color(0xFFE9EFE8),
    onSurface = Color(0xFF5F391C),
    tertiary = Color(0xFFEFA48B)
)

@Composable
fun CryptTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> avocado
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}