package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model

import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedSkillInfo

data class ActiveDotUIModel(
    val skillId: String,
    val remainingTurns: Int,
    val skillInfo: UsedSkillInfo.DamageOverTime
)
