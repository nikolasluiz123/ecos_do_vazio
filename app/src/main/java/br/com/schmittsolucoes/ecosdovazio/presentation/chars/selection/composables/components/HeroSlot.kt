package br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.model.CharSelectionUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HeroButtonStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HeroSlotBackgroundBottom
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HeroSlotBackgroundTop
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.NewCharacterButtonBackground
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.pictureTextHighlightBackground
import coil.compose.SubcomposeAsyncImage

private const val ROUNDED_CORNER_SHAPE = 4
private const val NEW_CHAR_ROUNDED_CORNER_SHAPE = 8

@Composable
fun HeroSlot(
    charModel: CharSelectionUIModel,
    onClick: (CharSelectionUIModel) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .widthIn(max = 200.dp)
            .fillMaxWidth()
            .aspectRatio(0.9f)
            .shadow(4.dp, RoundedCornerShape(ROUNDED_CORNER_SHAPE.dp))
            .clip(RoundedCornerShape(ROUNDED_CORNER_SHAPE.dp))
            .clickable(enabled = !charModel.isLoading) { onClick(charModel) }
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        HeroSlotBackgroundTop,
                        HeroSlotBackgroundBottom
                    )
                )
            )
            .border(
                width = 1.dp,
                color = HeroButtonStrokeColor,
                shape = RoundedCornerShape(ROUNDED_CORNER_SHAPE.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (charModel.isLoading) {
            CircularProgressIndicator(
                color = Highlight,
                strokeWidth = 2.dp
            )
        } else {
            charModel.presentationImage?.let { image ->
                HeroImage(image)
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = if (charModel.name == null) Arrangement.Center else Arrangement.Bottom
            ) {
                if (charModel.name == null) {
                    AddHeroIcon()
                    Spacer(modifier = Modifier.height(20.dp))
                }

                HeroName(charModel)
            }
        }
    }
}

@Composable
private fun HeroImage(image: Int) {
    SubcomposeAsyncImage(
        model = image,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        filterQuality = FilterQuality.Medium,
        loading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Highlight,
                    strokeWidth = 2.dp
                )
            }
        }
    )
}

@Composable
private fun AddHeroIcon() {
    Box(
        modifier = Modifier
            .size(56.dp)
            .border(
                width = 1.dp,
                color = HeroButtonStrokeColor,
                shape = RoundedCornerShape(NEW_CHAR_ROUNDED_CORNER_SHAPE.dp)
            )
            .background(
                color = NewCharacterButtonBackground,
                shape = RoundedCornerShape(NEW_CHAR_ROUNDED_CORNER_SHAPE.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = HeroButtonStrokeColor,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
private fun HeroName(charModel: CharSelectionUIModel) {
    Text(
        text = charModel.name ?: stringResource(R.string.new_hero_label),
        style = MaterialTheme.typography.labelMedium,
        color = if (charModel.name != null) Color.LightGray else HeroButtonStrokeColor,
        modifier = if (charModel.name != null) {
            Modifier
                .padding(8.dp)
                .background(color = pictureTextHighlightBackground)
                .padding(12.dp, 4.dp)
        } else {
            Modifier
        }
    )
}