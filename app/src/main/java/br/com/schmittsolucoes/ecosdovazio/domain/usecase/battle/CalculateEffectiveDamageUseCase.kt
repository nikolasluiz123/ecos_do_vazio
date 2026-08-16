package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle

import kotlin.math.roundToLong

class CalculateEffectiveDamageUseCase {
    fun executeInternal(rawDamage: Long, damageReduction: Double, targetMultiplier: Double): Long {
        return (rawDamage * (1 - damageReduction) * (1 - targetMultiplier)).roundToLong()
    }
}
