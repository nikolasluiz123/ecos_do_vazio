package br.com.schmittsolucoes.ecosdovazio.presentation.components.bars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.AppUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HeroButtonStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.TopBarIcons
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.TopBarSubtitle
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.TopBarTitle
import coil.compose.SubcomposeAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    uiState: AppUIState,
    visible: Boolean = true,
    onLogout: () -> Unit = { }
) {
    AnimatedVisibility(
        visible = visible,
        enter = BarEnterTransition,
        exit = BarExitTransition
    ) {
        Surface(
            shadowElevation = 4.dp,
            tonalElevation = 8.dp
        ) {
            Column {
                TopAppBar(
                    title = {
                        TopBarCustomTitle(uiState)
                    },
                    navigationIcon = {
                        CharProfileImage(
                            imageRes = uiState.profileImageRes,
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                        )
                    },
                    actions = {
                        IconButton(onClick = onLogout) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = stringResource(R.string.logout_label),
                                tint = TopBarIcons
                            )
                        }

                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_settings_20dp),
                                contentDescription = stringResource(R.string.settings_label),
                                tint = TopBarIcons
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun TopBarCustomTitle(uiState: AppUIState) {
    Column {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            ),
            color = TopBarTitle
        )
        uiState.charHeader?.name?.let { charName ->
            Text(
                text = charName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif
                ),
                color = TopBarSubtitle
            )
        }
    }
}

@Composable
private fun CharProfileImage(
    imageRes: Int?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(
                width = 1.dp,
                color = HeroButtonStrokeColor,
                shape = RoundedCornerShape(4.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = imageRes,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.padding(12.dp),
                    color = Highlight,
                    strokeWidth = 2.dp
                )
            }
        )
    }
}
