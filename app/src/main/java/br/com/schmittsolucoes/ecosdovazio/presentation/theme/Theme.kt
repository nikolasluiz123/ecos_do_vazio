package br.com.schmittsolucoes.ecosdovazio.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = backgroundDark,
    surface = surfaceDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
    surfaceContainer = surfaceContainerDark,
    onSurfaceVariant = onSurfaceVariantDark,
    secondary = secondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark
)

private val LightColorScheme = lightColorScheme(
    background = backgroundLight,
    surface = surfaceLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
    surfaceContainer = surfaceContainerLight,
    onSurfaceVariant = onSurfaceVariantLight,
    secondaryContainer = secondaryContainerLight,
    secondary = secondaryLight,
    onSecondaryContainer = onSecondaryContainerLight
)

@Composable
fun EcosDoVazioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}