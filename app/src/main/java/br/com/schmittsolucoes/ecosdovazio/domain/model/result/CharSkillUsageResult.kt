package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

sealed interface CharSkillUsageResult {
    val refreshTime: Int

    data class CommonDamage(
        val newEnemyHealth: Long,
        override val refreshTime: Int,
    ) : CharSkillUsageResult

    data class DamageOverTime(
        val newEnemyHealth: Long,
        val repeat: Int,
        override val refreshTime: Int
    ) : CharSkillUsageResult

    data class Debuff(
        val newEnemyHealth: Long,
        val repeat: Int,
        val skillCategory: SkillCategory,
        val newMultiplier: Double,
        override val refreshTime: Int
    ) : CharSkillUsageResult

    data class Buff(
        val repeat: Int,
        val skillCategory: SkillCategory,
        val newMultiplier: Double,
        override val refreshTime: Int
    ) : CharSkillUsageResult
}