package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle

import kotlin.math.roundToLong

class CalculateRawHealUseCase {
    fun executeInternal(
        lifeRestore: Long,
        healAttributePoints: Long,
        multiplier: Double
    ): Long {
        return (lifeRestore + (healAttributePoints * multiplier)).roundToLong()
    }
}
