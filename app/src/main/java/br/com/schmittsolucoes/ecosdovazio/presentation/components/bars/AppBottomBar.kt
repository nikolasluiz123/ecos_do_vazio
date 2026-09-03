package br.com.schmittsolucoes.ecosdovazio.presentation.components.bars

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.navigation.CharRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.navigation.navigateToChar
import br.com.schmittsolucoes.ecosdovazio.presentation.history.navigation.HistoryRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.history.navigation.navigateToHistory
import br.com.schmittsolucoes.ecosdovazio.presentation.home.navigation.HomeRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.home.navigation.navigateToHome
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.navigation.CharSkillsRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.navigation.navigateToCharSkills

private val IconSize = 20.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppBottomBar(
    navController: NavHostController,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
    visible: Boolean = true,
    scrollBehavior: FloatingToolbarScrollBehavior? = null
) {
    AnimatedVisibility(
        visible = visible,
        enter = BarEnterTransition,
        exit = BarExitTransition
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val animatedAlpha = calculateBottomBarAlpha(scrollBehavior)

        HorizontalFloatingToolbar(
            modifier = Modifier.graphicsLayer {
                alpha = animatedAlpha
                translationY = (1f - animatedAlpha) * 16.dp.toPx()
            },
            expanded = isExpanded,
            scrollBehavior = scrollBehavior,
        ) {
            ExpandableToolbarVisibility(isExpanded = isExpanded) {
                Row {
                    BottomBarItem.entries.forEach { item ->
                        val selected = currentDestination?.route?.contains(item.route::class.qualifiedName.orEmpty()) == true

                        BottomBarItemTooltipBox(
                            item = item,
                            selected = selected,
                            onClick = {
                                if (!selected) {
                                    when (item) {
                                        BottomBarItem.Home -> navController.navigateToHome()
                                        BottomBarItem.Char -> navController.navigateToChar()
                                        BottomBarItem.Skills -> navController.navigateToCharSkills()
                                        BottomBarItem.History -> navController.navigateToHistory()
                                    }
                                }
                            }
                        )
                    }
                }
            }

            MoreHorizTooltipBox(
                isExpanded = isExpanded,
                onToggleExpanded = onToggleExpanded
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun calculateBottomBarAlpha(
    scrollBehavior: FloatingToolbarScrollBehavior?
): Float {
    val rawProgress = if ((scrollBehavior != null) && (scrollBehavior.state.offsetLimit != 0f)) {
        (1f + (scrollBehavior.state.offset / -scrollBehavior.state.offsetLimit)).coerceIn(0f, 1f)
    } else {
        1f
    }

    val smoothedProgress = FastOutSlowInEasing.transform(rawProgress)

    val animatedAlpha by animateFloatAsState(
        targetValue = smoothedProgress,
        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing),
        label = "bottomBarAlpha"
    )

    return animatedAlpha
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ExpandableToolbarVisibility(
    isExpanded: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = isExpanded,
        enter = FloatingToolbarDefaults.horizontalEnterTransition(expandFrom = Alignment.Start),
        exit = FloatingToolbarDefaults.horizontalExitTransition(shrinkTowards = Alignment.Start),
        content = { content() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomBarTooltipBox(
    label: String,
    content: @Composable () -> Unit
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
        tooltip = {
            PlainTooltip { Text(label) }
        },
        state = rememberTooltipState(),
        content = content
    )
}

@Composable
private fun BottomBarItemTooltipBox(
    item: BottomBarItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val label = stringResource(item.label)

    BottomBarTooltipBox(label = label) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(item.icon),
                contentDescription = label,
                tint = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                modifier = Modifier.size(IconSize)
            )
        }
    }
}

@Composable
private fun MoreHorizTooltipBox(
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit
) {
    val toggleLabel = stringResource(
        if (isExpanded) R.string.bottom_menu_collapse else R.string.bottom_menu_more_options
    )

    BottomBarTooltipBox(label = toggleLabel) {
        IconButton(onClick = onToggleExpanded) {
            Icon(
                imageVector = if (isExpanded) Icons.Default.Close else Icons.Default.MoreHoriz,
                contentDescription = toggleLabel,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(IconSize)
            )
        }
    }
}

private enum class BottomBarItem(
    val route: Any,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    Home(HomeRoute, R.string.bottom_menu_home, R.drawable.ic_home_16dp),
    Char(CharRoute, R.string.bottom_menu_char, R.drawable.ic_char_16dp),
    Skills(CharSkillsRoute, R.string.bottom_menu_skills, R.drawable.ic_skills_16dp),
    History(HistoryRoute, R.string.bottom_menu_history, R.drawable.ic_history_16dp)
}
