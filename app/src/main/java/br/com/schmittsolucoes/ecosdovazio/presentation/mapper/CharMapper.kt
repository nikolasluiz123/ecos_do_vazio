package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.core.formatters.NumberFormatter
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttributes
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharLevelInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharLevelInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.model.CharSelectionUIModel
import javax.inject.Inject

class CharMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val numberFormatter: NumberFormatter,
) {

    fun mapToUIModel(char: CharSelection): CharSelectionUIModel {
        return CharSelectionUIModel(
            id = char.id,
            name = char.name,
            presentationImage = char.presentationImageName?.let(resourcesProvider::getClassImage),
        )
    }

    fun mapToUIModel(charLevelInfo: CharLevelInfo): CharLevelInfoUIModel {
        val progress = getLevelProgress(charLevelInfo)
        return CharLevelInfoUIModel(
            level = charLevelInfo.level.toString(),
            currentExperience = charLevelInfo.experience.toString(),
            nextLevelExperience = charLevelInfo.nextLevelExperience.toString(),
            progress = progress,
        )
    }

    fun mapToUIModel(charLevelInfo: CharLevelInfo, progress: Float): CharLevelInfoUIModel {
        return CharLevelInfoUIModel(
            level = charLevelInfo.level.toString(),
            currentExperience = charLevelInfo.experience.toString(),
            nextLevelExperience = charLevelInfo.nextLevelExperience.toString(),
            progress = progress,
        )
    }

    fun mapToUIModel(charStatus: CharStatus): CharStatusUIModel {
        return CharStatusUIModel(
            hp = charStatus.hp.toString(),
            baseDamage = charStatus.baseDamage.toString(),
            physicalResistance = numberFormatter.formatPercentage(charStatus.physicalResistance),
            magicResistance = numberFormatter.formatPercentage(charStatus.magicResistance),
            criticalChance = numberFormatter.formatPercentage(charStatus.criticalChance),
            dodgeChance = numberFormatter.formatPercentage(charStatus.dodgeChance),
        )
    }

    fun mapAttributesToUIModel(
        attributes: CharAttributes?,
        availablePoints: Long,
    ): List<CharAttributesUIModel>? {
        return attributes?.attributes?.map { attr ->
            val attributeProgress = getAttributeProgress(attributes, attr.attribute.totalValue)
            mapToUIModel(
                identifiedCharAttribute = attr,
                progress = attributeProgress,
                canIncrement = availablePoints > 0,
                canDecrement = attr.attribute.charValue > 0,
            )
        }
    }

    fun mapToUIModel(
        identifiedCharAttribute: IdentifiedCharAttribute,
        progress: Float,
        canIncrement: Boolean,
        canDecrement: Boolean,
    ): CharAttributesUIModel {
        return CharAttributesUIModel(
            identifier = identifiedCharAttribute.id,
            totalValue = identifiedCharAttribute.attribute.totalValue.toString(),
            progress = progress,
            canIncrement = canIncrement,
            canDecrement = canDecrement,
        )
    }

    private fun getLevelProgress(levelInfo: CharLevelInfo): Float {
        return if (levelInfo.nextLevelExperience > 0L) {
            levelInfo.experience.toFloat() / levelInfo.nextLevelExperience.toFloat()
        } else {
            0f
        }
    }

    private fun getAttributeProgress(
        attributes: CharAttributes,
        totalValue: Long,
    ): Float {
        return if (attributes.maxAttributeValue > 0) {
            totalValue.toFloat() / attributes.maxAttributeValue.toFloat()
        } else {
            0f
        }
    }
}
