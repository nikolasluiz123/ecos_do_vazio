package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.ui.Alignment

internal val VerticalGridEnterTransition: EnterTransition =
    fadeIn(animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            expandHorizontally(
                animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing),
                expandFrom = Alignment.End
            )

internal val VerticalGridExitTransition: ExitTransition =
    fadeOut(animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            shrinkHorizontally(
                animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing),
                shrinkTowards = Alignment.End
            )

internal val HorizontalGridEnterTransition: EnterTransition =
    fadeIn(animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            expandVertically(
                animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing),
                expandFrom = Alignment.Bottom
            )

internal val HorizontalGridExitTransition: ExitTransition =
    fadeOut(animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            shrinkVertically(
                animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing),
                shrinkTowards = Alignment.Bottom
            )