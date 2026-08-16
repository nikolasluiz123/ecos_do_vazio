package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.DamageType
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateMagicResistanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculatePhysicalResistanceUseCase
import javax.inject.Inject

class GetMobDamageReductionUseCase @Inject constructor(
    private val calculatePhysicalResistanceUseCase: CalculatePhysicalResistanceUseCase,
    private val calculateMagicResistanceUseCase: CalculateMagicResistanceUseCase
) {

    fun executeInternal(battleMobInfo: BattleMobInfo): Double {
        return when (val damageType = getDamageType(battleMobInfo.mobCategory)) {
            DamageType.PHYSICAL -> {
                calculatePhysicalResistanceUseCase.executeInternal(
                    points = getResistancePoints(damageType, battleMobInfo.attributes),
                    factor = getPhysicalFactor(battleMobInfo.mobCategory),
                    maxResistance = getMaxPhysicalResistance(battleMobInfo.mobCategory)
                )
            }

            DamageType.MAGICAL -> {
                calculateMagicResistanceUseCase.executeInternal(
                    points = getResistancePoints(damageType, battleMobInfo.attributes),
                    factor = getMagicalFactor(battleMobInfo.mobCategory),
                    maxResistance = getMaxMagicalResistance(battleMobInfo.mobCategory)
                )
            }
        }
    }

    private fun getDamageType(category: MobCategory): DamageType {
        return when (category) {
            MobCategory.WARRIOR -> DamageType.PHYSICAL
            MobCategory.MAGE -> DamageType.MAGICAL
            MobCategory.HEALER -> DamageType.MAGICAL
            MobCategory.ORC_WARRIOR -> DamageType.PHYSICAL
        }
    }

    private fun getResistancePoints(damageType: DamageType, attributes: Mob.Attributes): Long {
        return when (damageType) {
            DamageType.PHYSICAL -> attributes.physicalResistance
            DamageType.MAGICAL -> attributes.magicResistance
        }
    }

    private fun getPhysicalFactor(category: MobCategory): Double {
        return when (category) {
            MobCategory.WARRIOR -> 1.0
            MobCategory.MAGE -> 0.5
            MobCategory.HEALER -> 0.5
            MobCategory.ORC_WARRIOR -> 2.0
        }
    }

    private fun getMaxPhysicalResistance(category: MobCategory): Double {
        return when (category) {
            MobCategory.WARRIOR -> 0.5
            MobCategory.MAGE -> 0.3
            MobCategory.HEALER -> 0.3
            MobCategory.ORC_WARRIOR -> 0.75
        }
    }

    private fun getMagicalFactor(category: MobCategory): Double {
        return when (category) {
            MobCategory.WARRIOR -> 0.5
            MobCategory.MAGE -> 1.0
            MobCategory.HEALER -> 1.0
            MobCategory.ORC_WARRIOR -> 0.8
        }
    }

    private fun getMaxMagicalResistance(category: MobCategory): Double {
        return when (category) {
            MobCategory.WARRIOR -> 0.3
            MobCategory.MAGE -> 0.5
            MobCategory.HEALER -> 0.5
            MobCategory.ORC_WARRIOR -> 0.4
        }
    }
}