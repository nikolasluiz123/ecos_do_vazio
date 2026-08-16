package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle

import br.com.schmittsolucoes.ecosdovazio.domain.usecase.constants.GameConstants.MAGIC_RESISTANCE_SCALE_CONSTANT

class CalculateMagicResistanceUseCase {
    fun executeInternal(points: Long, factor: Double, maxResistance: Double): Double {
        val effectiveResistance = points * factor
        val calculatedResistance = (effectiveResistance / (MAGIC_RESISTANCE_SCALE_CONSTANT + effectiveResistance))

        return minOf(maxResistance, calculatedResistance)
    }
}
