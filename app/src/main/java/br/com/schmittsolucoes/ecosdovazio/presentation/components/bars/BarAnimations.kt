package br.com.schmittsolucoes.ecosdovazio.presentation.components.bars

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically

const val BAR_ANIMATION_DURATION = 600

val BarEnterTransition: EnterTransition =
    fadeIn(animationSpec = tween(BAR_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            expandVertically(animationSpec = tween(BAR_ANIMATION_DURATION, easing = FastOutSlowInEasing))

val BarExitTransition: ExitTransition =
    fadeOut(animationSpec = tween(BAR_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            shrinkVertically(animationSpec = tween(BAR_ANIMATION_DURATION, easing = FastOutSlowInEasing))
