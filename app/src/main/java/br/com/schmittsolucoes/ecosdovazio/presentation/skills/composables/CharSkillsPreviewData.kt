package br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.IdentifiedSkillAttribute
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.CharSkillsUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel

object CharSkillsPreviewData {

    val skillBastion = CharSkillDetailsUIModel(
        id = "1",
        name = "Bastião",
        description = "Aumenta a defesa física e mágica em 20% durante 3 turnos.",
        skillCategory = SkillCategory.DEFENSIVE_BUFF,
        damage = null,
        multiplier = null,
        duration = 3,
        refreshTime = 4,
        minLevel = 1,
        attributes = listOf(
            IdentifiedSkillAttribute(AttributeIdentifier.STRENGTH, 10),
            IdentifiedSkillAttribute(AttributeIdentifier.PHYSICAL_RESISTANCE, 15),
        ),
        image = R.drawable.skill_bastiao,
        blocked = false,
    )

    val skillFireball = CharSkillDetailsUIModel(
        id = "2",
        name = "Bola de Fogo",
        description = "Lança uma esfera de fogo que causa 150 de dano mágico de fogo.",
        skillCategory = SkillCategory.DAMAGE,
        damage = 150,
        multiplier = 1.5,
        duration = null,
        refreshTime = 2,
        minLevel = 1,
        attributes = listOf(
            IdentifiedSkillAttribute(AttributeIdentifier.INTELLIGENCE, 12),
        ),
        image = R.drawable.skill_bola_de_fogo,
        blocked = false,
    )

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
        skillBastion,
        skillFireball,
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
        selectedSkill = skillBastion,
        availablePoints = 3,
        selectedSkillAttributes = selectedSkillAttributes,
    )

    val uiStateWithError = CharSkillsUIState(
        skills = skillList,
        errorMessage = "Ocorreu um erro ao carregar as habilidades.",
    )
}
