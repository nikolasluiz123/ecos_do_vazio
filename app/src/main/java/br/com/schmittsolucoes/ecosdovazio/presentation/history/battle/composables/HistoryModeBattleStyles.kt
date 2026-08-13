package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
internal fun getLevelStyle(containerWidth: Dp): TextStyle {
    return when {
        containerWidth >= 250.dp -> MaterialTheme.typography.titleLarge
        containerWidth >= 180.dp -> MaterialTheme.typography.titleMedium
        else -> MaterialTheme.typography.titleSmall
    }.copy(
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.Serif
    )
}

@Composable
internal fun getNameStyle(containerWidth: Dp): TextStyle {
    return when {
        containerWidth >= 250.dp -> MaterialTheme.typography.titleMedium
        containerWidth >= 180.dp -> MaterialTheme.typography.titleSmall
        else -> MaterialTheme.typography.labelMedium
    }.copy(
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.Serif
    )
}