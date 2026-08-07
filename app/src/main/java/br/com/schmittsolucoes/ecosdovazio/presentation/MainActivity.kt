package br.com.schmittsolucoes.ecosdovazio.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.CharSelectionRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.navigation.ClassSelectionRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.components.LoadingOverlay
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

                    Box(modifier = Modifier.fillMaxSize()) {
                        App(
                            uiState = uiState,
                            navController = navController,
                            snackbarHostState = snackbarHostState,
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
    content: @Composable () -> Unit = { }
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showTopBar = currentRoute != null &&
            !currentRoute.contains(CharSelectionRoute::class.qualifiedName.orEmpty()) &&
            !currentRoute.contains(ClassSelectionRoute::class.qualifiedName.orEmpty())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                uiState = uiState,
                isVisible = showTopBar
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(
    uiState: AppUIState,
    isVisible: Boolean
) {
    if (isVisible) {
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
                            modifier = Modifier.padding(start = 16.dp, end = 12.dp)
                        )
                    },
                    actions = {
                        Icon(
                            painter = painterResource(R.drawable.ic_settings_20dp),
                            contentDescription = stringResource(R.string.settings_label),
                            modifier = Modifier.padding(end = 16.dp),
                            tint = TopBarIcons
                        )
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
            style = MaterialTheme.typography.titleLarge.copy(
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