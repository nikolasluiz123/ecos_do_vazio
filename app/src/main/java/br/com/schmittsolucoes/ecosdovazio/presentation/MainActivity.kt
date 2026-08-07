package br.com.schmittsolucoes.ecosdovazio.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.components.LoadingOverlay
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

        splashScreen.setKeepOnScreenCondition {
            viewModel.isInitializing.value
        }

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val appErrorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
            val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
            val loadingMessage by viewModel.loadingMessage.collectAsStateWithLifecycle()
            val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()
            val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()
            val isInitializing by viewModel.isInitializing.collectAsStateWithLifecycle()

            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(snackbarMessage) {
                snackbarMessage?.let {
                    snackbarHostState.showSnackbar(it)
                    viewModel.onDismissSnackbar()
                }
            }

            EcosDoVazioTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    Box(modifier = Modifier.fillMaxSize()) {
                        App(
                            snackbarHostState = snackbarHostState,
                        ) {
                            if (!isInitializing) {
                                AppNavHost(
                                    navController = navController,
                                    windowSizeClass = windowSizeClass,
                                    startDestination = startDestination
                                )
                            }

                            appErrorMessage?.let { message ->
                                ErrorDialog(
                                    message = message,
                                    onDismiss = viewModel::onDismissErrorDialog
                                )
                            }
                        }

                        if (isLoading) {
                            loadingMessage?.let { LoadingOverlay(message = it) } ?: LoadingOverlay()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun App(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable () -> Unit = { }
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
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