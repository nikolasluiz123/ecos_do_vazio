package br.com.schmittsolucoes.ecosdovazio.presentation.components.bars

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.navigation.CharRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.navigation.navigateToChar
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.navigation.HistoryMobsInfoRoute
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
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val collapsedItems = getCollapsedItemsForDestination(currentDestination)
    var previousDestination by remember { mutableStateOf<NavDestination?>(null) }

    if (currentDestination != previousDestination) {
        previousDestination = currentDestination
        val shouldBeExpanded = collapsedItems.isEmpty()

        if (isExpanded != shouldBeExpanded) {
            onToggleExpanded()
        }
    }

    val fabHorizontalBias by animateFloatAsState(
        targetValue = if (isExpanded) 0f else -1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "fabHorizontalBias"
    )

    AnimatedVisibility(
        visible = visible,
        enter = BarEnterTransition,
        exit = BarExitTransition
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = BiasAlignment(fabHorizontalBias, 0f)
        ) {
            val animatedAlpha = calculateBottomBarAlpha(scrollBehavior)

            HorizontalFloatingToolbar(
                modifier = Modifier.graphicsLayer {
                    alpha = animatedAlpha
                    translationY = (1f - animatedAlpha) * 16.dp.toPx()
                },
                expanded = isExpanded,
                colors = FloatingToolbarDefaults.standardFloatingToolbarColors(
                    toolbarContainerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.85f)
                ),
                scrollBehavior = scrollBehavior,
            ) {
                ExpandableToolbarVisibility(isExpanded = isExpanded) {
                    Row {
                        BottomBarItem.entries.forEach { item ->
                            val selected = isItemSelected(item, currentDestination)

                            BottomBarItemTooltipBox(
                                item = item,
                                selected = selected,
                                onClick = {
                                    navigateToBottomBarItem(navController, item, currentDestination)
                                }
                            )
                        }
                    }
                }

                CollapsedToolbarVisibility(isExpanded = isExpanded) {
                    Row {
                        collapsedItems.forEach { item ->
                            val selected = isItemSelected(item, currentDestination)

                            BottomBarItemTooltipBox(
                                item = item,
                                selected = selected,
                                onClick = {
                                    navigateToBottomBarItem(navController, item, currentDestination)
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
}

private fun getCollapsedItemsForDestination(destination: NavDestination?): List<BottomBarItem> {
    return when {
        destination?.hasRoute<HistoryMobsInfoRoute>() == true -> listOf(BottomBarItem.History)
        else -> emptyList()
    }
}

private fun isItemSelected(item: BottomBarItem, destination: NavDestination?): Boolean {
    if (destination?.hasRoute(item.route::class) == true) return true

    return when (item) {
        BottomBarItem.History -> destination?.hasRoute<HistoryMobsInfoRoute>() == true
        else -> false
    }
}

private fun navigateToBottomBarItem(
    navController: NavHostController,
    item: BottomBarItem,
    destination: NavDestination?
) {
    val isExactDestination = destination?.hasRoute(item.route::class) == true

    if (!isExactDestination) {
        when (item) {
            BottomBarItem.Home -> navController.navigateToHome()
            BottomBarItem.Char -> navController.navigateToChar()
            BottomBarItem.Skills -> navController.navigateToCharSkills()
            BottomBarItem.History -> navController.navigateToHistory()
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun CollapsedToolbarVisibility(
    isExpanded: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = !isExpanded,
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
