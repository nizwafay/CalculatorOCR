package com.example.calculatorocr.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.calculatorocr.BuildConfig

private val RedDarkColorScheme = darkColorScheme(
    primary = red_dark_primary,
    secondary = red_dark_secondary,
    tertiary = red_dark_tertiary
)

private val RedLightColorScheme = lightColorScheme(
    primary = red_light_primary,
    secondary = red_light_secondary,
    tertiary = red_light_tertiary
)

private val GreenDarkColorScheme = darkColorScheme(
    primary = green_dark_primary,
    secondary = green_dark_secondary,
    tertiary = green_dark_tertiary
)

private val GreenLightColorScheme = lightColorScheme(
    primary = green_light_primary,
    secondary = green_light_secondary,
    tertiary = green_light_tertiary
)

@Composable
fun CalculatorOCRTheme(
    flavor: String = BuildConfig.FLAVOR,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        flavor.contains("red") -> {
            if (darkTheme) RedDarkColorScheme
            else RedLightColorScheme
        }

        else -> {
            if (darkTheme) GreenDarkColorScheme
            else GreenLightColorScheme
        }
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}