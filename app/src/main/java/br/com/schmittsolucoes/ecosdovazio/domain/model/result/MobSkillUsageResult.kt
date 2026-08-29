package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

sealed interface MobSkillUsageResult {
    val refreshTime: Int

    data class CommonDamage(
        val newEnemyHealth: Long,
        override val refreshTime: Int,
    ) : MobSkillUsageResult

    data class DamageOverTime(
        val newEnemyHealth: Long,
        val repeat: Int,
        val skillId: String,
        override val refreshTime: Int
    ) : MobSkillUsageResult

    data class Debuff(
        val newEnemyHealth: Long,
        val repeat: Int,
        val skillId: String,
        val skillCategory: SkillCategory,
        val newMultiplier: Double,
        override val refreshTime: Int
    ) : MobSkillUsageResult

    data class Buff(
        val repeat: Int,
        val skillId: String,
        val mobId: String,
        val skillCategory: SkillCategory,
        val newMultiplier: Double,
        override val refreshTime: Int
    ) : MobSkillUsageResult
}
