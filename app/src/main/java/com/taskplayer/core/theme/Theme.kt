package com.taskplayer.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary          = PrimaryGold,
    onPrimary        = BackgroundDark,
    background       = BackgroundDark,
    onBackground     = TextPrimary,
    surface          = SurfaceDark,
    onSurface        = TextPrimary,
    surfaceVariant   = CardDark,
    onSurfaceVariant = TextSecondary,
    secondary        = AccentBlue,
    onSecondary      = TextPrimary,
    error            = AccentRed
)

@Composable
fun TaskPlayerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography  = TaskPlayerTypography,
        content     = content
    )
}