package br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.CharUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharLevelInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharStatusUIModel

object CharPreviewData {

    val levelInfo = CharLevelInfoUIModel(
        level = "12",
        currentExperience = "1250",
        nextLevelExperience = "2500",
        progress = 0.5f
    )

    val statusInfo = CharStatusUIModel(
        hp = "450",
        baseDamage = "85",
        physicalResistance = "12.50%",
        magicResistance = "8.00%",
        criticalChance = "15.00%",
        dodgeChance = "5.00%"
    )

    val attributesInfo = listOf(
        CharAttributesUIModel(
            identifier = AttributeIdentifier.STRENGTH,
            totalValue = "25",
            progress = 0.5f,
            canIncrement = true,
            canDecrement = true
        ),
        CharAttributesUIModel(
            identifier = AttributeIdentifier.DEXTERITY,
            totalValue = "18",
            progress = 0.36f,
            canIncrement = true,
            canDecrement = true
        ),
        CharAttributesUIModel(
            identifier = AttributeIdentifier.INTELLIGENCE,
            totalValue = "10",
            progress = 0.2f,
            canIncrement = true,
            canDecrement = false
        ),
        CharAttributesUIModel(
            identifier = AttributeIdentifier.VITALITY,
            totalValue = "30",
            progress = 0.6f,
            canIncrement = true,
            canDecrement = true
        ),
        CharAttributesUIModel(
            identifier = AttributeIdentifier.AGILITY,
            totalValue = "12",
            progress = 0.24f,
            canIncrement = true,
            canDecrement = true
        ),
        CharAttributesUIModel(
            identifier = AttributeIdentifier.PHYSICAL_RESISTANCE,
            totalValue = "15",
            progress = 0.3f,
            canIncrement = true,
            canDecrement = true
        ),
        CharAttributesUIModel(
            identifier = AttributeIdentifier.MAGIC_RESISTANCE,
            totalValue = "10",
            progress = 0.2f,
            canIncrement = true,
            canDecrement = true
        )
    )

    val uiStateLoaded = CharUIState(
        levelInfo = levelInfo,
        statusInfo = statusInfo,
        attributesInfo = attributesInfo,
        availablePoints = 3
    )

    val uiStateWithError = CharUIState(
        levelInfo = levelInfo,
        statusInfo = statusInfo,
        attributesInfo = attributesInfo,
        availablePoints = 0,
        errorMessage = "Ocorreu um erro ao atualizar os atributos."
    )
}
