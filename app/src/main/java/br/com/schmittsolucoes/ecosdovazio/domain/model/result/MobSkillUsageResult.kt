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

    data class VampiricDamage(
        val newEnemyHealth: Long,
        val newCharHealth: Long,
        val mobId: String,
        override val refreshTime: Int
    ) : MobSkillUsageResult

    data class Debuff(
        val newEnemyHealth: Long,
        val repeat: Int,
        val skillId: String,
        val skillCategory: SkillCategory,
        override val refreshTime: Int
    ) : MobSkillUsageResult

    data class Buff(
        val repeat: Int,
        val skillId: String,
        val mobId: String,
        val skillCategory: SkillCategory,
        override val refreshTime: Int
    ) : MobSkillUsageResult

    data class Heal(
        val newMobHealth: Long,
        val targetMobId: String,
        override val refreshTime: Int
    ) : MobSkillUsageResult

    data class AreaHeal(
        val newMobsHealth: Map<String, Long>,
        override val refreshTime: Int
    ) : MobSkillUsageResult
}
