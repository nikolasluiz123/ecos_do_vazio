package br.com.schmittsolucoes.ecosdovazio.presentation.theme.animations

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun animateNumericStringAsState(targetValue: String, durationMillis: Int, label: String): State<String> {
    val floatTarget = targetValue.toFloatOrNull() ?: 0f
    
    val animatedValue = animateFloatAsState(
        targetValue = floatTarget,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing
        ),
        label = label
    )
    
    val currentTargetValue = rememberUpdatedState(targetValue)
    
    return remember {
        derivedStateOf {
            if (currentTargetValue.value.toLongOrNull() != null) {
                animatedValue.value.toLong().toString()
            } else {
                currentTargetValue.value
            }
        }
    }
}

@Composable
fun animateNumericStringProgressBarState(targetValue: String, label: String): State<String> {
    return animateNumericStringAsState(
        targetValue = targetValue,
        durationMillis = AnimationConstants.PROGRESS_BAR_DURATION_MS,
        label = label
    )
}
