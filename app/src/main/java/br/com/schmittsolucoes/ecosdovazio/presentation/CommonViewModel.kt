package br.com.schmittsolucoes.ecosdovazio.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

const val DEBUG_PROCESS_TAG = "DEBUG_PROCESS_TAG"
const val STATE_IN_STOP_TIMEOUT_MILLIS = 5000L

abstract class CommonViewModel : ViewModel() {

    abstract fun getErrorMessageFrom(throwable: Throwable): String

    abstract fun onShowErrorDialog(message: String)

    fun launch(block: suspend (scope: CoroutineScope) -> Unit): Job {
        return viewModelScope.launch(exceptionHandler) {
            block(this)
        }
    }

    protected open fun onError(throwable: Throwable) {
        Log.e(DEBUG_PROCESS_TAG, "${this::class.simpleName} - ${throwable.message}", throwable)
    }

    protected fun onShowCommonError(throwable: Throwable) {
        val message = getErrorMessageFrom(throwable)
        onShowErrorDialog(message)
        onError(throwable)
    }

    protected fun <T> Flow<T>.stateInWithCommonError(
        initialValue: T,
        scope: CoroutineScope = viewModelScope,
        started: SharingStarted = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        onError: (suspend FlowCollector<T>.(Throwable) -> Unit)? = null,
    ): StateFlow<T> {
        return this
            .catch { throwable ->
                onShowCommonError(throwable)
                onError?.invoke(this, throwable)
            }
            .stateIn(
                scope = scope,
                started = started,
                initialValue = initialValue,
            )
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onShowCommonError(throwable)
    }
}
