package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharLevelInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharLevelInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.model.CharSelectionUIModel

fun CharSelection.toUIModel(presentationDrawableId: Int?): CharSelectionUIModel {
    return CharSelectionUIModel(
        id = id,
        name = name,
        presentationImage = presentationDrawableId
    )
}

fun CharLevelInfo.toUIModel(progress: Float): CharLevelInfoUIModel {
    return CharLevelInfoUIModel(
        level = level.toString(),
        currentExperience = experience.toString(),
        nextLevelExperience = nextLevelExperience.toString(),
        progress = progress
    )
}

fun IdentifiedCharAttribute.toUIModel(progress: Float): CharAttributesUIModel {
    return CharAttributesUIModel(
        identifier = id,
        totalValue = attribute.totalValue.toString(),
        progress = progress
    )
}
