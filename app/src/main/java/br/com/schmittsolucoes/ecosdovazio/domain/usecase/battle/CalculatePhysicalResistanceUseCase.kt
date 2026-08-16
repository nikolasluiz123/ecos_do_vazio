package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle

import br.com.schmittsolucoes.ecosdovazio.domain.usecase.constants.GameConstants.PHYSICAL_RESISTANCE_SCALE_CONSTANT

class CalculatePhysicalResistanceUseCase {
    fun executeInternal(points: Long, factor: Double, maxResistance: Double): Double {
        val effectiveResistance = points * factor
        val calculatedResistance = (effectiveResistance / (PHYSICAL_RESISTANCE_SCALE_CONSTANT + effectiveResistance))

        return minOf(maxResistance, calculatedResistance)
    }
}
