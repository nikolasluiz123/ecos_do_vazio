package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharLevelInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharLevelInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.model.CharSelectionUIModel
import javax.inject.Inject

class CharMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) {

    fun mapToUIModel(char: CharSelection): CharSelectionUIModel {
        return CharSelectionUIModel(
            id = char.id,
            name = char.name,
            presentationImage = char.presentationImageName?.let(resourcesProvider::getClassImage)
        )
    }

    fun mapToUIModel(charLevelInfo: CharLevelInfo, progress: Float): CharLevelInfoUIModel {
        return CharLevelInfoUIModel(
            level = charLevelInfo.level.toString(),
            currentExperience = charLevelInfo.experience.toString(),
            nextLevelExperience = charLevelInfo.nextLevelExperience.toString(),
            progress = progress
        )
    }

    fun mapToUIModel(
        identifiedCharAttribute: IdentifiedCharAttribute,
        progress: Float,
        canIncrement: Boolean,
        canDecrement: Boolean
    ): CharAttributesUIModel {
        return CharAttributesUIModel(
            identifier = identifiedCharAttribute.id,
            totalValue = identifiedCharAttribute.attribute.totalValue.toString(),
            progress = progress,
            canIncrement = canIncrement,
            canDecrement = canDecrement
        )
    }
}
