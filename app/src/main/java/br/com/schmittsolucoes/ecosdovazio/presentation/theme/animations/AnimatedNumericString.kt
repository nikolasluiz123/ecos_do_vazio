package br.com.schmittsolucoes.ecosdovazio.presentation.theme.animations

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

@Composable
fun animateNumericStringAsState(targetValue: String, durationMillis: Int, label: String): String {
    val floatTarget = targetValue.toFloatOrNull() ?: 0f
    
    val animatedValue by animateFloatAsState(
        targetValue = floatTarget,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing
        ),
        label = label
    )
    
    return if (targetValue.toLongOrNull() != null) {
        animatedValue.toLong().toString()
    } else {
        targetValue
    }
}

@Composable
fun animateNumericStringProgressBarState(targetValue: String, label: String): String {
    return animateNumericStringAsState(
        targetValue = targetValue,
        durationMillis = AnimationConstants.PROGRESS_BAR_DURATION_MS,
        label = label
    )
}
