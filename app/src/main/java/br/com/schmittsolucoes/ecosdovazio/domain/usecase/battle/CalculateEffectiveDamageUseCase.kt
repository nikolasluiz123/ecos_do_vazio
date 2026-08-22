package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle

import kotlin.math.roundToLong
import kotlin.random.Random

class CalculateEffectiveDamageUseCase {
    fun executeInternal(
        rawDamage: Long,
        damageReduction: Double,
        targetMultiplier: Double,
        criticalChance: Double,
        dodgeChance: Double,
    ): Long {
        val isDodge = Random.nextDouble() <= dodgeChance
        if (isDodge) return 0

        val isCritical = Random.nextDouble() <= criticalChance
        val criticalMultiplier = if (isCritical) 1.5 else 1.0

        return (rawDamage * (1 - damageReduction) * (1 - targetMultiplier) * criticalMultiplier).roundToLong()
    }
}
