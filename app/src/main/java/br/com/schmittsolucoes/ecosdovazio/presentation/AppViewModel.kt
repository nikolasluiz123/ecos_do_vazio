package br.com.schmittsolucoes.ecosdovazio.presentation

import android.content.Context
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.manager.LoadingManager
import br.com.schmittsolucoes.ecosdovazio.domain.manager.SnackbarManager
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHeaderUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.InitializeDatabaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.preferences.UnselectCharUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.CharSelectionRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private data class AppInternalState(
    val isInitializing: Boolean = true,
    val startDestination: Any = CharSelectionRoute,
    val errorMessage: String? = null,
    val isToolbarExpanded: Boolean = true,
)

sealed interface AppNavigationEvent {
    data object Logout : AppNavigationEvent
}

@HiltViewModel
class AppViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val snackbarManager: SnackbarManager,
    private val initializeDatabaseUseCase: InitializeDatabaseUseCase,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val resourcesProvider: ResourcesProvider,
    private val unselectCharUseCase: UnselectCharUseCase,
    getCharHeaderUseCase: GetCharHeaderUseCase,
    loadingManager: LoadingManager,
) : CommonViewModel() {

    private val _internalState = MutableStateFlow(AppInternalState())

    private val _navigationChannel = Channel<AppNavigationEvent>(Channel.BUFFERED)
    val navigationEvent: Flow<AppNavigationEvent> = _navigationChannel.receiveAsFlow()

    val uiState: StateFlow<AppUIState> = combine(
        _internalState,
        loadingManager.isLoading,
        loadingManager.message,
        snackbarManager.message,
        getCharHeaderUseCase()
    ) { internalState, isLoading, loadingMessage, snackbarMessage, charHeader ->
        AppUIState(
            isInitializing = internalState.isInitializing,
            startDestination = internalState.startDestination,
            errorMessage = internalState.errorMessage,
            isLoading = isLoading,
            loadingMessage = loadingMessage,
            snackbarMessage = snackbarMessage,
            charHeader = charHeader,
            profileImageRes = charHeader?.let { resourcesProvider.getCharProfileImage(it.profileImageName) },
            isToolbarExpanded = internalState.isToolbarExpanded
        )
    }.stateInWithCommonError(initialValue = AppUIState())

    init {
        launch {
            initializeDatabaseUseCase()
            checkInitialDestination()
            _internalState.update { it.copy(isInitializing = false) }
        }
    }

    private suspend fun checkInitialDestination() {
        try {
            val user = userRepository.getFirstUser() ?: return
            val preferences = preferencesRepository.getUserPreferences(user.id).firstOrNull()

            if (preferences?.selectedCharId != null) {
                _internalState.update { it.copy(startDestination = MainGraph) }
            }
        } catch (_: Exception) {
            // Se falhar, mantemos o startDestination padrão (CharSelectionRoute)
        }
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return when (throwable) {
            is UserException.UserNotFound -> context.getString(R.string.user_error_not_found)
            else -> context.getString(R.string.error_unexpected)
        }
    }

    override fun onShowErrorDialog(message: String) {
        _internalState.update { it.copy(errorMessage = message) }
    }

    fun onDismissErrorDialog() {
        _internalState.update { it.copy(errorMessage = null) }
    }

    fun onDismissSnackbar() {
        snackbarManager.hideSnackbar()
    }

    fun toggleToolbarExpanded() {
        _internalState.update { it.copy(isToolbarExpanded = !it.isToolbarExpanded) }
    }

    fun logout() {
        launch {
            unselectCharUseCase()
            _navigationChannel.send(AppNavigationEvent.Logout)
        }
    }
}
