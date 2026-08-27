package br.com.schmittsolucoes.ecosdovazio.domain.model.battle

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo

interface ActiveStatus {
    val skillId: String
    val remainingTurns: Int
}

sealed interface CharActiveStatus: ActiveStatus {
    data class DoT(
        override val skillId: String,
        override val remainingTurns: Int,
        val skillInfo: UsedCharSkillInfo.DamageOverTime
    ) : CharActiveStatus

    data class Debuff(
        override val skillId: String,
        override val remainingTurns: Int,
        val skillCategory: SkillCategory,
        val skillInfo: UsedCharSkillInfo.Debuff
    ) : CharActiveStatus
}

sealed interface MobActiveStatus: ActiveStatus {
    data class DoT(
        override val skillId: String,
        override val remainingTurns: Int,
        val sourceId: String,
        val skillInfo: UsedMobSkillInfo.DamageOverTime
    ) : MobActiveStatus

    data class Debuff(
        override val skillId: String,
        override val remainingTurns: Int,
        val sourceId: String,
        val skillCategory: SkillCategory,
        val skillInfo: UsedMobSkillInfo.Debuff
    ) : MobActiveStatus
}
