package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.DamageType
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateMagicResistanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculatePhysicalResistanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharMagicResistanceFactorUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharMagicResistanceMaxUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharPhysicalResistanceFactorUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharPhysicalResistanceMaxUseCase

class GetCharDamageReductionUseCase(
    private val calculatePhysicalResistanceUseCase: CalculatePhysicalResistanceUseCase,
    private val calculateMagicResistanceUseCase: CalculateMagicResistanceUseCase,
    private val getCharPhysicalResistanceFactorUseCase: GetCharPhysicalResistanceFactorUseCase,
    private val getCharPhysicalResistanceMaxUseCase: GetCharPhysicalResistanceMaxUseCase,
    private val getCharMagicResistanceFactorUseCase: GetCharMagicResistanceFactorUseCase,
    private val getCharMagicResistanceMaxUseCase: GetCharMagicResistanceMaxUseCase
) {
    fun executeInternal(battleCharInfo: BattleCharInfo): Double {
        return when (getDamageType(battleCharInfo.classCategory)) {
            DamageType.PHYSICAL -> {
                val points = battleCharInfo.attributes.find { it.id == AttributeIdentifier.PHYSICAL_RESISTANCE }?.attribute?.totalValue ?: 0L
                val factor = getCharPhysicalResistanceFactorUseCase.executeInternal(battleCharInfo.classCategory)
                val maxResistance = getCharPhysicalResistanceMaxUseCase.executeInternal(battleCharInfo.classCategory)

                calculatePhysicalResistanceUseCase.executeInternal(
                    points = points,
                    factor = factor,
                    maxResistance = maxResistance
                )
            }

            DamageType.MAGICAL -> {
                val points = battleCharInfo.attributes.find { it.id == AttributeIdentifier.MAGIC_RESISTANCE }?.attribute?.totalValue ?: 0L
                val factor = getCharMagicResistanceFactorUseCase.executeInternal(battleCharInfo.classCategory)
                val maxResistance = getCharMagicResistanceMaxUseCase.executeInternal(battleCharInfo.classCategory)

                calculateMagicResistanceUseCase.executeInternal(
                    points = points,
                    factor = factor,
                    maxResistance = maxResistance
                )
            }
        }
    }

    private fun getDamageType(category: ClassCategory): DamageType {
        return when (category) {
            ClassCategory.WARRIOR, ClassCategory.ARCHER -> DamageType.PHYSICAL
            ClassCategory.MAGE -> DamageType.MAGICAL
        }
    }
}
