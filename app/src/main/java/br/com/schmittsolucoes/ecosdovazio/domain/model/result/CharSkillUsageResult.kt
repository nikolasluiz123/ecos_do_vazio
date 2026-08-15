package br.com.schmittsolucoes.ecosdovazio.domain.model.result

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
}