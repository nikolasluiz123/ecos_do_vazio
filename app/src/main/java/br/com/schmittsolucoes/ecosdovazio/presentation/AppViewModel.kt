package br.com.schmittsolucoes.ecosdovazio.presentation

import android.app.Application
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.manager.LoadingManager
import br.com.schmittsolucoes.ecosdovazio.domain.manager.SnackbarManager
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.InitializeDatabaseUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.CharSelectionRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.home.navigation.HomeRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val application: Application,
    private val snackbarManager: SnackbarManager,
    private val initializeDatabaseUseCase: InitializeDatabaseUseCase,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    loadingManager: LoadingManager,
) : CommonViewModel() {

    private val _isInitializing = MutableStateFlow(true)
    val isInitializing = _isInitializing.asStateFlow()

    private val _startDestination = MutableStateFlow<Any>(CharSelectionRoute)
    val startDestination = _startDestination.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    val isLoading = loadingManager.isLoading
    val loadingMessage = loadingManager.message

    val snackbarMessage = snackbarManager.message

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
                _startDestination.value = HomeRoute
            }
        } catch (e: Exception) {
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
}
