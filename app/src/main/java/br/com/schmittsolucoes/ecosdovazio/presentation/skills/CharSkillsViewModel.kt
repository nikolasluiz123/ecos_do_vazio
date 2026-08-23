package br.com.schmittsolucoes.ecosdovazio.presentation.skills

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttributes
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkillDetails
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CharAttributesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.DecrementAttributeUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetAvailableAttributesUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.IncrementAttributeUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharSkillsDetailsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.toUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharSkillsViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    charSkillsDetailsQueryUseCase: CharSkillsDetailsQueryUseCase,
    getAvailableAttributesUseCase: GetAvailableAttributesUseCase,
    charAttributesQueryUseCase: CharAttributesQueryUseCase,
    private val incrementAttributeUseCase: IncrementAttributeUseCase,
    private val decrementAttributeUseCase: DecrementAttributeUseCase,
    private val resourcesProvider: ResourcesProvider
) : CommonViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _selectedSkill = MutableStateFlow<CharSkillDetailsUIModel?>(null)

    val uiState: StateFlow<CharSkillsUIState> = combine(
        _errorMessage,
        _selectedSkill,
        charSkillsDetailsQueryUseCase(),
        getAvailableAttributesUseCase(),
        charAttributesQueryUseCase()
    ) { errorMessage, selectedSkill, skills, availablePoints, charAttributes ->
        val selectedSkillAttributes = getSelectedSkillAttributes(selectedSkill, charAttributes, availablePoints)

        CharSkillsUIState(
            errorMessage = errorMessage,
            skills = mapCharSkillDetailsToUIModel(skills),
            selectedSkill = selectedSkill,
            availablePoints = availablePoints,
            selectedSkillAttributes = selectedSkillAttributes
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = CharSkillsUIState()
    )

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun onSelectSkill(skill: CharSkillDetailsUIModel) {
        _selectedSkill.value = skill
    }

    fun onDismissSkillDetails() {
        _selectedSkill.value = null
    }

    fun onIncrementAttribute(identifier: AttributeIdentifier) {
        launch {
            incrementAttributeUseCase(identifier)
        }
    }

    fun onDecrementAttribute(identifier: AttributeIdentifier) {
        launch {
            decrementAttributeUseCase(identifier)
        }
    }

    private fun getSelectedSkillAttributes(
        selectedSkill: CharSkillDetailsUIModel?,
        charAttributes: CharAttributes?,
        availablePoints: Long
    ): List<CharAttributesUIModel> {
        if (selectedSkill == null || charAttributes == null) return emptyList()

        val requiredSkillAttributes = selectedSkill.attributes.filter { it.attribute > 0 }
        val requiredSkillAttributeIdentifiers = requiredSkillAttributes.map { it.id }

        val charAttributesFiltered = charAttributes.attributes.filter { attr ->
            attr.id in requiredSkillAttributeIdentifiers
        }

        return requiredSkillAttributes.map { skillAttribute ->
            val charAttribute = charAttributesFiltered.first { it.id == skillAttribute.id }
            val charAttributeValue = charAttribute.attribute.totalValue.toFloat()
            val progress = charAttributeValue / skillAttribute.attribute.toFloat()

            charAttribute.toUIModel(
                progress = progress,
                canIncrement = availablePoints > 0,
                canDecrement = charAttribute.attribute.charValue > 0
            )
        }
    }

    private fun mapCharSkillDetailsToUIModel(skills: List<CharSkillDetails>): List<CharSkillDetailsUIModel> {
        return skills.map { skill ->
            val image = resourcesProvider.getSkillImage(skill.imageName) ?: 0
            skill.toUIModel(image = image)
        }
    }
}
