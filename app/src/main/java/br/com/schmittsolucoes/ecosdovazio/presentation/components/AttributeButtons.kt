package br.com.schmittsolucoes.ecosdovazio.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val BUTTON_SIZE = 32
private const val BUTTON_ICON_SIZE = 16

@Composable
fun AttributeIncrementButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier.size(BUTTON_SIZE.dp),
        enabled = enabled
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.size(BUTTON_ICON_SIZE.dp)
        )
    }
}

@Composable
fun AttributeDecrementButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier.size(BUTTON_SIZE.dp),
        enabled = enabled
    ) {
        Icon(
            imageVector = Icons.Default.Remove,
            contentDescription = null,
            modifier = Modifier.size(BUTTON_ICON_SIZE.dp)
        )
    }
}
