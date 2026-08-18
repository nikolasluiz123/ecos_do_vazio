package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill

class ChooseMobSkillUseCase {
    fun executeInternal(skills: List<MobSkill>): MobSkill? {
        val availableSkills = skills.filter { !it.blocked && it.currentRefreshTime == 0 }
        return availableSkills.minByOrNull { it.refreshTime }
    }
}