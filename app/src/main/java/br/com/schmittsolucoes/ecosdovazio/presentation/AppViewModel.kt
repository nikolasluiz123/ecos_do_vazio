package br.com.schmittsolucoes.ecosdovazio.presentation

import android.app.Application
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.manager.LoadingManager
import br.com.schmittsolucoes.ecosdovazio.domain.manager.SnackbarManager
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharHeader
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHeaderUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.InitializeDatabaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.preferences.UnselectCharUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.CharSelectionRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val application: Application,
    private val snackbarManager: SnackbarManager,
    private val initializeDatabaseUseCase: InitializeDatabaseUseCase,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val resourcesProvider: ResourcesProvider,
    private val unselectCharUseCase: UnselectCharUseCase,
    getCharHeaderUseCase: GetCharHeaderUseCase,
    loadingManager: LoadingManager,
) : CommonViewModel() {

    private val _isInitializing = MutableStateFlow(true)
    private val _startDestination = MutableStateFlow<Any>(CharSelectionRoute)
    private val _errorMessage = MutableStateFlow<String?>(null)

    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent: SharedFlow<Unit> = _logoutEvent.asSharedFlow()

    val uiState: StateFlow<AppUIState> = combine(
        _isInitializing,
        _startDestination,
        _errorMessage,
        loadingManager.isLoading,
        loadingManager.message,
        snackbarManager.message,
        getCharHeaderUseCase()
    ) { flows ->
        AppUIState(
            isInitializing = flows[0] as Boolean,
            startDestination = flows[1] as Any,
            errorMessage = flows[2] as String?,
            isLoading = flows[3] as Boolean,
            loadingMessage = flows[4] as String?,
            snackbarMessage = flows[5] as String?,
            charHeader = flows[6] as CharHeader?,
            profileImageRes = (flows[6] as? CharHeader)?.let { resourcesProvider.getProfileClassImage(it.profileImageName) }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = AppUIState()
    )

    init {
        launch {
            initializeDatabaseUseCase()
            checkInitialDestination()
            _isInitializing.value = false
        }
    }

    private suspend fun checkInitialDestination() {
        try {
            val user = userRepository.getFirstUser()
            val preferences = preferencesRepository.getUserPreferences(user.id).firstOrNull()

            if (preferences?.selectedCharId != null) {
                _startDestination.value = MainGraph
            }
        } catch (_: Exception) {
            // Se falhar, mantemos o startDestination padrão (CharSelectionRoute)
        }
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return application.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun onDismissSnackbar() {
        snackbarManager.hideSnackbar()
    }

    fun logout() {
        launch {
            unselectCharUseCase()
            _logoutEvent.emit(Unit)
        }
    }
}
