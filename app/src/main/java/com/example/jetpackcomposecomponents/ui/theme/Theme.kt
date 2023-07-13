package com.example.jetpackcomposecomponents.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Black800,
    primaryVariant = Black800,
    secondary = Black800,
    background = Black800,
    surface = Black800,

    onPrimary = White100,
    onSecondary = White100,
    onBackground = White100,
    onSurface = White100
)

private val LightColorPalette = lightColors(
    primary = White100,
    primaryVariant = White100,
    secondary = White100,
    background = White100,
    surface = White100,

    onPrimary = Black800,
    onSecondary = Black800,
    onBackground = Black800,
    onSurface = Black800
)

@Composable
fun JetpackComponentsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        if (darkTheme) {
            systemUiController.setSystemBarsColor(
                color = Black800
            )
        } else {
            systemUiController.setSystemBarsColor(
                color = White100
            )
        }
    }

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}