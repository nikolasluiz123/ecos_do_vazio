package br.com.schmittsolucoes.ecosdovazio.presentation.specialization.selection

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.SpecializationSelection
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.SpecializationsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.SetCharSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.CharException
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.SpecializationMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SpecializationSelectionViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val specializationMapper: SpecializationMapper,
    private val setCharSpecializationUseCase: SetCharSpecializationUseCase,
    specializationsQueryUseCase: SpecializationsQueryUseCase
) : CommonViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _selectedSpecializationId = MutableStateFlow<String?>(null)

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome

    private val _specializations = specializationsQueryUseCase().map { list ->
        list.map { mapDomainToUIModel(it) }
    }

    val uiState: StateFlow<SpecializationSelectionUIState> = combine(
        _specializations,
        _errorMessage,
        _selectedSpecializationId
    ) { specializations, errorMessage, selectedSpecializationId ->
        SpecializationSelectionUIState(
            specializations = specializations,
            errorMessage = errorMessage,
            selectedSpecializationId = selectedSpecializationId
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = SpecializationSelectionUIState()
    )

    private fun mapDomainToUIModel(specializationSelection: SpecializationSelection): SelectionItemUIModel {
        return specializationMapper.mapToUIModel(specializationSelection)
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return when (throwable) {
            is CharException.SpecializationSelectionRequired -> context.getString(R.string.char_error_specialization_required)
            is UserException.UserNotFound -> context.getString(R.string.user_error_not_found)
            else -> context.getString(R.string.error_unexpected)
        }
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun onSelectSpecialization(specializationId: String) {
        _selectedSpecializationId.value = specializationId

        launch {
            setCharSpecializationUseCase(specializationId)
                .onSuccess { _navigateToHome.value = true }
                .onFailure { onShowErrorDialog(getErrorMessageFrom(it)) }
        }
    }

    fun onNavigatedToHome() {
        _navigateToHome.value = false
    }
}
