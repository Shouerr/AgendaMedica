package com.example.agendamedica.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = TextOnPrimary,
    secondary = GreenSecondary,
    background = BackgroundLight,
    onBackground = TextOnBackgroundLight,
    surface = Color.White,
    onSurface = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    onPrimary = TextOnPrimary,
    secondary = GreenSecondary,
    background = BackgroundDark,
    onBackground = TextOnBackgroundDark,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White
)

@Composable
fun AgendaMedicaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
