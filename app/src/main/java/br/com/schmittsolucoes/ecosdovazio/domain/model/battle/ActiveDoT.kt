package br.com.schmittsolucoes.ecosdovazio.domain.model.battle

import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo

sealed interface ActiveDoT {
    val skillId: String
    val remainingTurns: Int

    data class CharActiveDoT(
        override val skillId: String,
        override val remainingTurns: Int,
        val skillInfo: UsedCharSkillInfo.DamageOverTime
    ) : ActiveDoT

    data class MobActiveDoT(
        override val skillId: String,
        override val remainingTurns: Int,
        val sourceId: String,
        val skillInfo: UsedMobSkillInfo.DamageOverTime
    ) : ActiveDoT

    fun copyWithRemainingTurns(remainingTurns: Int): ActiveDoT {
        return when (this) {
            is CharActiveDoT -> copy(remainingTurns = remainingTurns)
            is MobActiveDoT -> copy(remainingTurns = remainingTurns)
        }
    }
}
