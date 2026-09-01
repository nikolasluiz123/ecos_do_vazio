package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory.AREA_DAMAGE
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory.DAMAGE
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory.DAMAGE_OVER_TIME
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory.DEFENSIVE_DEBUFF
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory.OFFENSIVE_DEBUFF
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory.VAMPIRIC_DAMAGE
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.ProjectedDamageInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobDamageReductionUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharDamageAttributePointsUseCase
import kotlin.math.roundToLong

class CalculateProjectedDamageUseCase(
    private val getCharDamageAttributePointsUseCase: GetCharDamageAttributePointsUseCase,
    private val getMobDamageReductionUseCase: GetMobDamageReductionUseCase
) {
    operator fun invoke(
        skill: CharSkill,
        charInfo: BattleCharInfo,
        mobInfo: BattleMobInfo?
    ): ProjectedDamageInfo? {
        val isDamageSkill = skill.skillCategory in listOf(DAMAGE, AREA_DAMAGE, DAMAGE_OVER_TIME, VAMPIRIC_DAMAGE, OFFENSIVE_DEBUFF, DEFENSIVE_DEBUFF)

        if (!isDamageSkill) return null

        val skillDamage = skill.damage
        val damageAttributePoints = getCharDamageAttributePointsUseCase.executeInternal(
            attributes = charInfo.attributes,
            classCategory = charInfo.classCategory
        )

        val baseDamage = skillDamage + damageAttributePoints
        
        val buffMultiplier = (charInfo.offensiveMultiplier - 1.0).coerceAtLeast(0.0)
        val buffBonus = (damageAttributePoints * buffMultiplier).roundToLong()
        
        val rawDamage = baseDamage + buffBonus

        if (mobInfo == null) {
            return ProjectedDamageInfo(
                totalDamage = rawDamage,
                baseDamage = baseDamage,
                attributeBonus = 0,
                buffBonus = buffBonus,
                defenseReduction = 0,
                targetBuffReduction = 0
            )
        }

        val damageReduction = getMobDamageReductionUseCase.executeInternal(mobInfo)
        

        val effectiveDamage = (rawDamage * (1 - damageReduction) * (1 - mobInfo.defensiveMultiplier)).roundToLong()

        val damageAfterNaturalReduction = (rawDamage * (1 - damageReduction)).roundToLong()
        val naturalReduction = rawDamage - damageAfterNaturalReduction
        val buffReduction = damageAfterNaturalReduction - effectiveDamage

        return ProjectedDamageInfo(
            totalDamage = effectiveDamage,
            baseDamage = baseDamage,
            attributeBonus = 0,
            buffBonus = buffBonus,
            defenseReduction = naturalReduction,
            targetBuffReduction = buffReduction
        )
    }
}
