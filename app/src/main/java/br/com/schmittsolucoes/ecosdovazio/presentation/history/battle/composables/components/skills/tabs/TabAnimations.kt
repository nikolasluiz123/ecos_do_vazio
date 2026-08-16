package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

internal val TabRowAnimationSpec = spring(
    stiffness = Spring.StiffnessMediumLow,
    visibilityThreshold = 0.1f
)