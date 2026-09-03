package br.com.schmittsolucoes.ecosdovazio.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.navigateToCharSelection
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.components.LoadingOverlay
import br.com.schmittsolucoes.ecosdovazio.presentation.components.bars.AppBottomBar
import br.com.schmittsolucoes.ecosdovazio.presentation.components.bars.AppTopBar
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
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
                            onLogout = viewModel::logout,
                            onToggleToolbarExpanded = viewModel::toggleToolbarExpanded
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun App(
    uiState: AppUIState,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onLogout: () -> Unit = { },
    onToggleToolbarExpanded: () -> Unit = { },
    content: @Composable () -> Unit = { }
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isMainGraph = currentDestination?.hierarchy?.any { it.hasRoute<MainGraph>() }
        ?: (uiState.startDestination == MainGraph)

    val scrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(
        exitDirection = FloatingToolbarExitDirection.Bottom
    )

    val fabHorizontalBias by animateFloatAsState(
        targetValue = if (uiState.isToolbarExpanded) 0f else -1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "fabHorizontalBias"
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior),
        topBar = {
            AppTopBar(
                uiState = uiState,
                visible = isMainGraph,
                onLogout = onLogout
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = BiasAlignment(fabHorizontalBias, 0f)
            ) {
                AppBottomBar(
                    navController = navController,
                    isExpanded = uiState.isToolbarExpanded,
                    onToggleExpanded = onToggleToolbarExpanded,
                    visible = isMainGraph,
                    scrollBehavior = scrollBehavior
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
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
