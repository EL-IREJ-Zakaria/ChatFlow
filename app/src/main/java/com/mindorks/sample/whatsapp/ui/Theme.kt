package com.mindorks.sample.whatsapp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primaryWhatsAppColor,
    primaryVariant = purple700,
    secondary = teal200,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
)

private val LightColorPalette = lightColors(
    primary = primaryWhatsAppColor,
    primaryVariant = purple700,
    secondary = teal200,
    background = Color.White,
    surface = Color.White
)

@Composable
fun WhatsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
