package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model

import androidx.annotation.DrawableRes
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedSkillInfo

data class ActiveDotUIModel(
    val skillId: String,
    val skillName: String,
    val skillDescription: String,
    val remainingTurns: Int,
    val skillInfo: UsedSkillInfo.DamageOverTime,
    @DrawableRes val skillImage: Int
)
