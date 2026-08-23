package br.com.schmittsolucoes.ecosdovazio.presentation.skills

import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel

data class CharSkillsUIState(
    val errorMessage: String? = null,
    val skills: List<CharSkillDetailsUIModel> = emptyList(),
    val selectedSkill: CharSkillDetailsUIModel? = null,
    val availablePoints: Long = 0,
    val selectedSkillAttributes: List<CharAttributesUIModel> = emptyList()
)
