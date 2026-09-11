package br.com.schmittsolucoes.ecosdovazio.presentation.theme.animations

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <S> AnimatedVerticalSlideContent(
    targetState: S,
    modifier: Modifier = Modifier,
    label: String = "AnimatedVerticalSlideContent",
    content: @Composable (targetState: S) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        modifier = modifier,
        transitionSpec = {
            if (targetState != initialState) {
                (slideInVertically { height -> height } + fadeIn()) togetherWith
                        (slideOutVertically { height -> -height } + fadeOut())
            } else {
                fadeIn() togetherWith fadeOut()
            }
        },
        label = label
    ) { targetValue ->
        content(targetValue)
    }
}
