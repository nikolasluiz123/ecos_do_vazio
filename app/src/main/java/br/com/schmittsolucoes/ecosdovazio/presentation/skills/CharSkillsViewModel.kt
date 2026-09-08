package br.com.schmittsolucoes.ecosdovazio.presentation.skills

import android.content.Context
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttributes
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkillDetails
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CharAttributesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.DecrementAttributeUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetAvailableAttributesUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.IncrementAttributeUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharSkillsDetailsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.CharMapper
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.SkillMapper
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private data class CharSkillsInternalState(
    val errorMessage: String? = null,
    val selectedSkill: CharSkillDetailsUIModel? = null,
)

@HiltViewModel
class CharSkillsViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    charSkillsDetailsQueryUseCase: CharSkillsDetailsQueryUseCase,
    getAvailableAttributesUseCase: GetAvailableAttributesUseCase,
    charAttributesQueryUseCase: CharAttributesQueryUseCase,
    private val incrementAttributeUseCase: IncrementAttributeUseCase,
    private val decrementAttributeUseCase: DecrementAttributeUseCase,
    private val skillMapper: SkillMapper,
    private val charMapper: CharMapper,
) : CommonViewModel() {

    private val _internalState = MutableStateFlow(CharSkillsInternalState())

    val uiState: StateFlow<CharSkillsUIState> = combine(
        _internalState,
        charSkillsDetailsQueryUseCase(),
        getAvailableAttributesUseCase(),
        charAttributesQueryUseCase(),
    ) { internalState, skills, availablePoints, charAttributes ->
        val selectedSkill = internalState.selectedSkill
        val selectedSkillAttributes = getSelectedSkillAttributes(selectedSkill, charAttributes, availablePoints)

        CharSkillsUIState(
            errorMessage = internalState.errorMessage,
            skills = mapCharSkillDetailsToUIModel(skills),
            selectedSkill = selectedSkill,
            availablePoints = availablePoints,
            selectedSkillAttributes = selectedSkillAttributes,
            isLoading = false,
        )
    }.stateInWithCommonError(
        initialValue = CharSkillsUIState(isLoading = true),
    )

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _internalState.update { it.copy(errorMessage = message) }
    }

    fun onDismissErrorDialog() {
        _internalState.update { it.copy(errorMessage = null) }
    }

    fun onSelectSkill(skill: CharSkillDetailsUIModel) {
        _internalState.update { it.copy(selectedSkill = skill) }
    }

    fun onDismissSkillDetails() {
        _internalState.update { it.copy(selectedSkill = null) }
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
        availablePoints: Long,
    ): List<CharAttributesUIModel> {
        if ((selectedSkill == null) || (charAttributes == null)) return emptyList()

        val requiredSkillAttributes = selectedSkill.attributes.filter { it.attribute > 0 }
        val requiredSkillAttributeIdentifiers = requiredSkillAttributes.map { it.id }

        val charAttributesFiltered = charAttributes.attributes.filter { attr ->
            attr.id in requiredSkillAttributeIdentifiers
        }

        return requiredSkillAttributes.map { skillAttribute ->
            val charAttribute = charAttributesFiltered.first { it.id == skillAttribute.id }
            val charAttributeValue = charAttribute.attribute.totalValue.toFloat()
            val progress = charAttributeValue / skillAttribute.attribute.toFloat()

            charMapper.mapToUIModel(
                identifiedCharAttribute = charAttribute,
                progress = progress,
                canIncrement = availablePoints > 0,
                canDecrement = charAttribute.attribute.charValue > 0,
            )
        }
    }

    private fun mapCharSkillDetailsToUIModel(skills: List<CharSkillDetails>): List<CharSkillDetailsUIModel> {
        return skills.map { skill ->
            skillMapper.mapToUIModel(skill)
        }
    }
}
