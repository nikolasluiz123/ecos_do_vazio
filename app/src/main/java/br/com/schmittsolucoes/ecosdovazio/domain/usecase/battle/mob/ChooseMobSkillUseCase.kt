package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill

class ChooseMobSkillUseCase {
    fun executeInternal(
        skills: List<MobSkill>,
        liveMobs: List<BattleMob> = emptyList(),
    ): MobSkill? {
        val availableSkills = skills.filter { (!it.blocked) && (it.currentRefreshTime == 0) }
        if (availableSkills.isEmpty()) return null

        val buffOrDebuffSkills = availableSkills.filter {
            it.skillCategory in BUFF_OR_DEBUFF_CATEGORIES
        }

        if (buffOrDebuffSkills.isNotEmpty()) {
            return buffOrDebuffSkills.minByOrNull { it.refreshTime }
        }

        val needsHeal = liveMobs.any { it.actualHealth < it.totalHealth }

        if (needsHeal) {
            val healSkills = availableSkills.filter {
                it.skillCategory in HEAL_CATEGORIES
            }

            if (healSkills.isNotEmpty()) {
                return healSkills.maxByOrNull { getSkillHeal(it) }
                    ?: healSkills.minByOrNull { it.refreshTime }
            }
        }

        val damageSkills = availableSkills.filter {
            it.skillCategory in DAMAGE_CATEGORIES
        }

        if (damageSkills.isNotEmpty()) {
            return damageSkills.maxWithOrNull(
                compareBy<MobSkill> { getSkillDamage(it) }
                    .thenByDescending { it.refreshTime },
            )
        }

        return null
    }

    private fun getSkillDamage(skill: MobSkill): Long {
        return when (skill) {
            is MobSkill.CommonDamage -> skill.damage
            is MobSkill.DamageOverTime -> skill.damage
            is MobSkill.VampiricDamage -> skill.damage
            else -> 0L
        }
    }

    private fun getSkillHeal(skill: MobSkill): Long {
        return (skill as? MobSkill.Heal)?.lifeRestore ?: 0L
    }

    companion object {
        private val BUFF_OR_DEBUFF_CATEGORIES = setOf(
            SkillCategory.OFFENSIVE_BUFF,
            SkillCategory.DEFENSIVE_BUFF,
            SkillCategory.OFFENSIVE_DEBUFF,
            SkillCategory.DEFENSIVE_DEBUFF,
        )

        private val HEAL_CATEGORIES = setOf(
            SkillCategory.HEAL,
            SkillCategory.AREA_HEAL,
        )

        private val DAMAGE_CATEGORIES = setOf(
            SkillCategory.DAMAGE,
            SkillCategory.DAMAGE_OVER_TIME,
            SkillCategory.VAMPIRIC_DAMAGE,
            SkillCategory.AREA_DAMAGE,
        )
    }
}