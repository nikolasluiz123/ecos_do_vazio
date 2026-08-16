package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle

import kotlin.math.roundToLong

class CalculateRawDamageUseCase {
    fun executeInternal(
        skillDamage: Long,
        damageAttributePoints: Long,
        multiplier: Double
    ): Long {
        return (skillDamage + (damageAttributePoints * multiplier)).roundToLong()
    }
}