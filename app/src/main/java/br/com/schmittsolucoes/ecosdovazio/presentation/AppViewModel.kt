package br.com.schmittsolucoes.ecosdovazio.presentation

import android.app.Application
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.manager.LoadingManager
import br.com.schmittsolucoes.ecosdovazio.domain.manager.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val application: Application,
    private val snackbarManager: SnackbarManager,
    loadingManager: LoadingManager,
) : CommonViewModel() {

    private val _isInitializing = MutableStateFlow(true)
    val isInitializing = _isInitializing.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    val isLoading = loadingManager.isLoading
    val loadingMessage = loadingManager.message

    val snackbarMessage = snackbarManager.message

    init {
        launch {
            _isInitializing.value = false
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
