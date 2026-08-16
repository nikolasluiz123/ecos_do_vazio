package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_CORNER_RADIUS
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HealthBarRedEnd
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HealthBarRedStart
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HealthBarTrack

@Composable
internal fun HealthBar(
    actualHealth: Long,
    totalHealth: Long,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "HealthBarProgress"
    )

    val animatedActualHealth by animateFloatAsState(
        targetValue = actualHealth.toFloat(),
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "HealthBarValue"
    )

    val cornerRadius = RoundedCornerShape(ITEM_CORNER_RADIUS)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp)
            .clip(cornerRadius)
            .background(HealthBarTrack.copy(alpha = 0.5f))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.3f),
                        Color.Transparent
                    )
                ),
                shape = cornerRadius
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .clip(cornerRadius)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            HealthBarRedStart.copy(alpha = 0.9f),
                            HealthBarRedEnd.copy(alpha = 0.9f)
                        )
                    )
                )
                .align(Alignment.CenterStart)
        )

        Text(
            text = "${animatedActualHealth.toLong()} / $totalHealth",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(1.5f, 1.5f),
                    blurRadius = 3f
                )
            ),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HealthBarPreview() {
    HealthBar(
        actualHealth = 75,
        totalHealth = 100,
        progress = 0.75f,
        modifier = Modifier.padding(16.dp)
    )
}
