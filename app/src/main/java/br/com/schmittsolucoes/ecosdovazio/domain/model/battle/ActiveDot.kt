package br.com.schmittsolucoes.ecosdovazio.domain.model.battle

import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedSkillInfo

data class ActiveDot(
    val skillId: String,
    val remainingTurns: Int,
    val skillInfo: UsedSkillInfo.DamageOverTime
)
