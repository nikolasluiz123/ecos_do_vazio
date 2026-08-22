package br.com.schmittsolucoes.ecosdovazio.presentation.chars

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.core.formatters.NumberFormatter
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttributes
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharLevelInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CharAttributesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetAvailableAttributesUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharBaseDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharCriticalChanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharDodgeChanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHPUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharLevelInfoUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharMagicResistanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharPhysicalResistanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.IncrementAttributeUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    getCharLevelInfoUseCase: GetCharLevelInfoUseCase,
    getCharHPUseCase: GetCharHPUseCase,
    getCharBaseDamageUseCase: GetCharBaseDamageUseCase,
    getCharPhysicalResistanceUseCase: GetCharPhysicalResistanceUseCase,
    getCharMagicResistanceUseCase: GetCharMagicResistanceUseCase,
    getCharCriticalChanceUseCase: GetCharCriticalChanceUseCase,
    getCharDodgeChanceUseCase: GetCharDodgeChanceUseCase,
    charAttributesQueryUseCase: CharAttributesQueryUseCase,
    getAvailableAttributesUseCase: GetAvailableAttributesUseCase,
    private val incrementAttributeUseCase: IncrementAttributeUseCase,
    private val numberFormatter: NumberFormatter
) : CommonViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<CharUIState> = combine(
        _errorMessage,
        getCharLevelInfoUseCase(),
        getCharHPUseCase(),
        getCharBaseDamageUseCase(),
        getCharPhysicalResistanceUseCase(),
        getCharMagicResistanceUseCase(),
        getCharCriticalChanceUseCase(),
        getCharDodgeChanceUseCase(),
        charAttributesQueryUseCase(),
        getAvailableAttributesUseCase()
    ) { values ->
        val errorMessage = values[0] as String?
        val levelInfo = values[1] as CharLevelInfo
        val hp = values[2] as Long
        val baseDamage = values[3] as Long
        val physRes = values[4] as Double
        val magRes = values[5] as Double
        val crit = values[6] as Double
        val dodge = values[7] as Double
        val attributes = values[8] as CharAttributes?
        val availablePoints = values[9] as Long

        val levelProgress = getLevelProgress(levelInfo)
        val attributesInfo = getAttributesInfo(attributes)

        CharUIState(
            errorMessage = errorMessage,
            levelInfo = levelInfo.toUIModel(levelProgress),
            statusInfo = CharStatusUIModel(
                hp = hp.toString(),
                baseDamage = baseDamage.toString(),
                physicalResistance = numberFormatter.formatPercentage(physRes),
                magicResistance = numberFormatter.formatPercentage(magRes),
                criticalChance = numberFormatter.formatPercentage(crit),
                dodgeChance = numberFormatter.formatPercentage(dodge)
            ),
            attributesInfo = attributesInfo,
            availablePoints = availablePoints
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = CharUIState()
    )

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return when (throwable) {
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

    fun onIncrementAttribute(identifier: AttributeIdentifier) {
        launch {
            incrementAttributeUseCase(identifier)
        }
    }

    private fun getLevelProgress(levelInfo: CharLevelInfo): Float {
        return if (levelInfo.nextLevelExperience > 0L) {
            levelInfo.experience.toFloat() / levelInfo.nextLevelExperience.toFloat()
        } else {
            0f
        }
    }

    private fun getAttributesInfo(attributes: CharAttributes?): List<CharAttributesUIModel>? =
        attributes?.attributes?.map { attr ->
            val attributeProgress = getAttributeProgress(attributes, attr.attribute.totalValue)
            attr.toUIModel(progress = attributeProgress)
        }

    private fun getAttributeProgress(
        attributes: CharAttributes,
        totalValue: Long
    ): Float {
        return if (attributes.maxAttributeValue > 0) {
            totalValue.toFloat() / attributes.maxAttributeValue.toFloat()
        } else {
            0f
        }
    }
}
