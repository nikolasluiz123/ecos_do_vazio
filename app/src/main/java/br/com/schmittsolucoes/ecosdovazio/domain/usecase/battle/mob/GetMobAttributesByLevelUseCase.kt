package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob

class GetMobAttributesByLevelUseCase(
    private val getMobPointsCountByLevelUseCase: GetMobPointsCountByLevelUseCase
) {
    operator fun invoke(
        level: Long,
        mobCategory: MobCategory,
        attributes: Mob.Attributes
    ): Mob.Attributes {
        val totalPoints = getMobPointsCountByLevelUseCase.executeInternal(mobCategory, level)
        val distribution = getAttributesPercentageDistribution()[mobCategory] ?: return attributes

        val calculatedPoints = mutableMapOf<AttributeIdentifier, Long>()
        val remainders = mutableMapOf<AttributeIdentifier, Double>()

        var allocatedPoints = 0L

        distribution.forEach { (attr, percentage) ->
            val points = totalPoints * percentage
            val integerPart = points.toLong()

            calculatedPoints[attr] = integerPart
            remainders[attr] = points - integerPart
            allocatedPoints += integerPart
        }

        val remainingToAllocate = totalPoints - allocatedPoints

        if (remainingToAllocate > 0) {
            val sortedByRemainder = remainders.entries.sortedByDescending { it.value }

            for (i in 0 until remainingToAllocate.toInt()) {
                val attr = sortedByRemainder[i % sortedByRemainder.size].key
                calculatedPoints[attr] = (calculatedPoints[attr] ?: 0L) + 1
            }
        }

        return attributes.copy(
            strength = attributes.strength + (calculatedPoints[AttributeIdentifier.STRENGTH] ?: 0),
            dexterity = attributes.dexterity + (calculatedPoints[AttributeIdentifier.DEXTERITY] ?: 0),
            intelligence = attributes.intelligence + (calculatedPoints[AttributeIdentifier.INTELLIGENCE] ?: 0),
            physicalResistance = attributes.physicalResistance + (calculatedPoints[AttributeIdentifier.PHYSICAL_RESISTANCE] ?: 0),
            magicResistance = attributes.magicResistance + (calculatedPoints[AttributeIdentifier.MAGIC_RESISTANCE] ?: 0),
            vitality = attributes.vitality + (calculatedPoints[AttributeIdentifier.VITALITY] ?: 0),
            agility = attributes.agility + (calculatedPoints[AttributeIdentifier.AGILITY] ?: 0)
        )
    }

    private fun getAttributesPercentageDistribution(): Map<MobCategory, Map<AttributeIdentifier, Double>> {
        return mapOf(
            MobCategory.WARRIOR to mapOf(
                AttributeIdentifier.STRENGTH to 0.6,
                AttributeIdentifier.DEXTERITY to 0.2,
                AttributeIdentifier.VITALITY to 0.1,
                AttributeIdentifier.PHYSICAL_RESISTANCE to 0.1
            ),
            MobCategory.MAGE to mapOf(
                AttributeIdentifier.INTELLIGENCE to 0.6,
                AttributeIdentifier.DEXTERITY to 0.2,
                AttributeIdentifier.VITALITY to 0.1,
                AttributeIdentifier.MAGIC_RESISTANCE to 0.1
            ),
            MobCategory.HEALER to mapOf(
                AttributeIdentifier.INTELLIGENCE to 0.4,
                AttributeIdentifier.DEXTERITY to 0.3,
                AttributeIdentifier.AGILITY to 0.1,
                AttributeIdentifier.MAGIC_RESISTANCE to 0.1,
                AttributeIdentifier.PHYSICAL_RESISTANCE to 0.05,
                AttributeIdentifier.VITALITY to 0.05,
            ),
            MobCategory.ORC_WARRIOR to mapOf(
                AttributeIdentifier.STRENGTH to 0.5,
                AttributeIdentifier.VITALITY to 0.2,
                AttributeIdentifier.PHYSICAL_RESISTANCE to 0.1,
                AttributeIdentifier.MAGIC_RESISTANCE to 0.1,
                AttributeIdentifier.DEXTERITY to 0.1,
            )
        )
    }
}