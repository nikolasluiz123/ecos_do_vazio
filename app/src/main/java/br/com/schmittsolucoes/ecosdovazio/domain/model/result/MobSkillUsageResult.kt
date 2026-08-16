package br.com.schmittsolucoes.ecosdovazio.domain.model.result

sealed interface MobSkillUsageResult {
    val refreshTime: Int

    data class CommonDamage(
        val newEnemyHealth: Long,
        override val refreshTime: Int,
    ) : MobSkillUsageResult

    data class DamageOverTime(
        val newEnemyHealth: Long,
        val repeat: Int,
        override val refreshTime: Int
    ) : MobSkillUsageResult
}
