package br.com.schmittsolucoes.ecosdovazio.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.navigation.CharRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.navigation.navigateToChar
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.navigateToCharSelection
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.components.LoadingOverlay
import br.com.schmittsolucoes.ecosdovazio.presentation.history.navigation.HistoryRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.history.navigation.navigateToHistory
import br.com.schmittsolucoes.ecosdovazio.presentation.home.navigation.HomeRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.home.navigation.navigateToHome
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.navigation.CharSkillsRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.navigation.navigateToCharSkills
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HeroButtonStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.TopBarIcons
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.TopBarSubtitle
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.TopBarTitle
import coil.compose.SubcomposeAsyncImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val snackbarHostState = remember { SnackbarHostState() }

            splashScreen.setKeepOnScreenCondition {
                uiState.isInitializing
            }

            LaunchedEffect(uiState.snackbarMessage) {
                uiState.snackbarMessage?.let {
                    snackbarHostState.showSnackbar(it)
                    viewModel.onDismissSnackbar()
                }
            }

            EcosDoVazioTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val windowSizeClass = calculateWindowSizeClass(this)

                    LaunchedEffect(Unit) {
                        viewModel.logoutEvent.collect {
                            navController.navigateToCharSelection(
                                navOptions = navOptions {
                                    popUpTo(0) { inclusive = true }
                                }
                            )
                        }
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        App(
                            uiState = uiState,
                            navController = navController,
                            snackbarHostState = snackbarHostState,
                            onLogout = viewModel::logout
                        ) {
                            if (!uiState.isInitializing) {
                                AppNavHost(
                                    navController = navController,
                                    windowSizeClass = windowSizeClass,
                                    startDestination = uiState.startDestination
                                )
                            }

                            uiState.errorMessage?.let { message ->
                                ErrorDialog(
                                    message = message,
                                    onDismiss = viewModel::onDismissErrorDialog
                                )
                            }
                        }

                        if (uiState.isLoading) {
                            uiState.loadingMessage?.let { LoadingOverlay(message = it) } ?: LoadingOverlay()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    uiState: AppUIState,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onLogout: () -> Unit = { },
    content: @Composable () -> Unit = { }
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isMainGraph = currentDestination?.hierarchy?.any { it.hasRoute<MainGraph>() }
        ?: (uiState.startDestination == MainGraph)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AnimatedVisibility(
                visible = isMainGraph,
                enter = BarEnterTransition,
                exit = BarExitTransition
            ) {
                AppTopBar(
                    uiState = uiState,
                    onLogout = onLogout
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isMainGraph,
                enter = BarEnterTransition,
                exit = BarExitTransition
            ) {
                AppBottomBar(navController = navController)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            content()
        }
    }
}

@Composable
private fun AppBottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        tonalElevation = 8.dp
    ) {
        BottomBarItem.entries.forEach { item ->
            val selected = currentDestination?.route?.contains(item.route::class.qualifiedName.orEmpty()) == true
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.label),
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = { Text(stringResource(item.label)) },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(
    uiState: AppUIState,
    onLogout: () -> Unit = { }
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

private const val BAR_ANIMATION_DURATION = 600

private val BarEnterTransition: EnterTransition =
    fadeIn(animationSpec = tween(BAR_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            expandVertically(animationSpec = tween(BAR_ANIMATION_DURATION, easing = FastOutSlowInEasing))

private val BarExitTransition: ExitTransition =
    fadeOut(animationSpec = tween(BAR_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            shrinkVertically(animationSpec = tween(BAR_ANIMATION_DURATION, easing = FastOutSlowInEasing))
