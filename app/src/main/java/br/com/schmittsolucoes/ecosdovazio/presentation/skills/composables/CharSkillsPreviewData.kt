package br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.IdentifiedSkillAttribute
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.CharSkillsUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel

object CharSkillsPreviewData {

    val skillWhirlwind = CharSkillDetailsUIModel(
        id = "3",
        name = "Redemoinho",
        description = "Gira atacando todos os inimigos próximos causando dano físico.",
        skillCategory = SkillCategory.AREA_DAMAGE,
        damage = 120,
        multiplier = 1.2,
        duration = null,
        refreshTime = 3,
        minLevel = 5,
        attributes = listOf(
            IdentifiedSkillAttribute(AttributeIdentifier.STRENGTH, 20),
            IdentifiedSkillAttribute(AttributeIdentifier.AGILITY, 15),
        ),
        image = R.drawable.skill_redemoinho,
        blocked = true,
    )

    val skillHeal = CharSkillDetailsUIModel(
        id = "4",
        name = "Cura Revigorante",
        description = "Restaura 200 pontos de vida do aliado alvo.",
        skillCategory = SkillCategory.HEAL,
        damage = null,
        multiplier = 1.0,
        duration = null,
        refreshTime = 3,
        minLevel = 3,
        attributes = listOf(
            IdentifiedSkillAttribute(AttributeIdentifier.INTELLIGENCE, 15),
        ),
        image = R.drawable.skill_cura_revigorante,
        blocked = false,
    )

    val skillList = listOf(
        skillWhirlwind,
        skillHeal,
    )

    val selectedSkillAttributes = listOf(
        CharAttributesUIModel(
            identifier = AttributeIdentifier.STRENGTH,
            totalValue = "8 / 10",
            progress = 0.8f,
            canIncrement = true,
            canDecrement = true,
        ),
        CharAttributesUIModel(
            identifier = AttributeIdentifier.PHYSICAL_RESISTANCE,
            totalValue = "15 / 15",
            progress = 1.0f,
            canIncrement = true,
            canDecrement = true,
        ),
    )

    val uiStateLoaded = CharSkillsUIState(
        skills = skillList,
    )

    val uiStateWithSelectedSkill = CharSkillsUIState(
        skills = skillList,
        selectedSkill = skillWhirlwind,
        availablePoints = 3,
        selectedSkillAttributes = selectedSkillAttributes,
    )

    val uiStateWithError = CharSkillsUIState(
        skills = skillList,
        errorMessage = "Ocorreu um erro ao carregar as habilidades.",
    )
}
