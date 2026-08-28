package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.core.formatters.NumberFormatter
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkillDetails
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.IdentifiedSkillAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel
import javax.inject.Inject
import javax.inject.Singleton

class SkillMapper @Inject constructor(
    private val numberFormatter: NumberFormatter,
    private val resourcesProvider: ResourcesProvider
) {

    fun mapToUIModel(skill: CharSkillDetails): CharSkillDetailsUIModel {
        return CharSkillDetailsUIModel(
            id = skill.id,
            name = skill.name,
            description = formatDescription(skill.description, skill.damage, skill.multiplier, skill.duration, skill.refreshTime),
            skillCategory = skill.skillCategory,
            damage = skill.damage,
            multiplier = skill.multiplier,
            duration = skill.duration,
            refreshTime = skill.refreshTime,
            minLevel = skill.minLevel,
            attributes = skill.attributes.toUIModelList(),
            image = resourcesProvider.getSkillImage(skill.imageName) ?: 0,
            blocked = skill.blocked
        )
    }

    fun mapToUIModel(
        skill: CharSkill,
        currentRefreshTime: Int,
        blocked: Boolean
    ): CharSkillUIModel {
        val image = resourcesProvider.getSkillImage(skill.imageName) ?: 0

        return when (skill) {
            is CharSkill.CommonDamage -> CharSkillUIModel.CommonDamage(
                id = skill.id,
                name = skill.name,
                description = formatDescription(skill.description, skill.damage, null, null, skill.refreshTime),
                refreshTime = skill.refreshTime,
                minLevel = skill.minLevel,
                image = image,
                attributes = skill.attributes,
                currentRefreshTime = currentRefreshTime,
                blocked = blocked,
                damage = skill.damage
            )

            is CharSkill.DamageOverTime -> CharSkillUIModel.DamageOverTime(
                id = skill.id,
                name = skill.name,
                description = formatDescription(skill.description, skill.damage, null, skill.duration, skill.refreshTime),
                refreshTime = skill.refreshTime,
                minLevel = skill.minLevel,
                image = image,
                attributes = skill.attributes,
                currentRefreshTime = currentRefreshTime,
                blocked = blocked,
                damage = skill.damage,
                duration = skill.duration
            )

            is CharSkill.Buff -> CharSkillUIModel.Buff(
                id = skill.id,
                name = skill.name,
                description = formatDescription(skill.description, null, skill.multiplier, skill.duration, skill.refreshTime),
                skillCategory = skill.skillCategory,
                refreshTime = skill.refreshTime,
                minLevel = skill.minLevel,
                image = image,
                attributes = skill.attributes,
                currentRefreshTime = currentRefreshTime,
                blocked = blocked,
                multiplier = skill.multiplier,
                duration = skill.duration
            )

            is CharSkill.Debuff -> CharSkillUIModel.Debuff(
                id = skill.id,
                name = skill.name,
                description = formatDescription(skill.description, skill.damage, skill.multiplier, skill.duration, skill.refreshTime),
                skillCategory = skill.skillCategory,
                refreshTime = skill.refreshTime,
                minLevel = skill.minLevel,
                image = image,
                attributes = skill.attributes,
                currentRefreshTime = currentRefreshTime,
                blocked = blocked,
                multiplier = skill.multiplier,
                duration = skill.duration,
                damage = skill.damage
            )
        }
    }

    fun mapToUIModel(skill: MobSkill): MobSkillUIModel {
        val image = resourcesProvider.getSkillImage(skill.imageName) ?: 0

        return when (skill) {
            is MobSkill.CommonDamage -> MobSkillUIModel.CommonDamage(
                id = skill.id,
                name = skill.name,
                description = formatDescription(skill.description, skill.damage, null, null, skill.refreshTime),
                image = image,
                refreshTime = skill.refreshTime,
                currentRefreshTime = skill.currentRefreshTime,
                blocked = skill.blocked,
                minLevel = skill.minLevel,
                damage = skill.damage
            )

            is MobSkill.DamageOverTime -> MobSkillUIModel.DamageOverTime(
                id = skill.id,
                name = skill.name,
                description = formatDescription(skill.description, skill.damage, null, skill.duration, skill.refreshTime),
                image = image,
                refreshTime = skill.refreshTime,
                currentRefreshTime = skill.currentRefreshTime,
                blocked = skill.blocked,
                minLevel = skill.minLevel,
                damage = skill.damage,
                duration = skill.duration
            )

            is MobSkill.Buff -> MobSkillUIModel.Buff(
                id = skill.id,
                name = skill.name,
                description = formatDescription(skill.description, null, skill.multiplier, skill.duration, skill.refreshTime),
                image = image,
                skillCategory = skill.skillCategory,
                refreshTime = skill.refreshTime,
                currentRefreshTime = skill.currentRefreshTime,
                blocked = skill.blocked,
                minLevel = skill.minLevel,
                multiplier = skill.multiplier,
                duration = skill.duration
            )

            is MobSkill.Debuff -> MobSkillUIModel.Debuff(
                id = skill.id,
                name = skill.name,
                description = formatDescription(skill.description, null, skill.multiplier, skill.duration, skill.refreshTime),
                image = image,
                skillCategory = skill.skillCategory,
                refreshTime = skill.refreshTime,
                currentRefreshTime = skill.currentRefreshTime,
                blocked = skill.blocked,
                minLevel = skill.minLevel,
                multiplier = skill.multiplier,
                duration = skill.duration
            )
        }
    }

    private fun formatDescription(
        description: String,
        damage: Long?,
        multiplier: Double?,
        duration: Int?,
        refreshTime: Int
    ): String {
        val multiplierStr = multiplier?.let { numberFormatter.formatPercentage(it) } ?: ""

        return try {
            description.format(damage ?: 0L, multiplierStr, duration ?: 0, refreshTime)
        } catch (_: Exception) {
            description
        }
    }

    private fun CharSkill.Attributes.toUIModelList() = listOf(
        IdentifiedSkillAttribute(AttributeIdentifier.STRENGTH, requiredStrength),
        IdentifiedSkillAttribute(AttributeIdentifier.DEXTERITY, requiredDexterity),
        IdentifiedSkillAttribute(AttributeIdentifier.INTELLIGENCE, requiredIntelligence),
        IdentifiedSkillAttribute(AttributeIdentifier.PHYSICAL_RESISTANCE, requiredPhysicalResistance),
        IdentifiedSkillAttribute(AttributeIdentifier.MAGIC_RESISTANCE, requiredMagicResistance),
        IdentifiedSkillAttribute(AttributeIdentifier.VITALITY, requiredVitality),
        IdentifiedSkillAttribute(AttributeIdentifier.AGILITY, requiredAgility),
    )
}

fun MobSkillUIModel.toDomain(): MobSkill {
    return when (this) {
        is MobSkillUIModel.CommonDamage -> MobSkill.CommonDamage(
            id = id,
            name = name,
            description = description,
            imageName = "",
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            damage = damage
        )

        is MobSkillUIModel.DamageOverTime -> MobSkill.DamageOverTime(
            id = id,
            name = name,
            description = description,
            imageName = "",
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            damage = damage,
            duration = duration
        )

        is MobSkillUIModel.Buff -> MobSkill.Buff(
            id = id,
            name = name,
            description = description,
            imageName = "",
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            multiplier = multiplier,
            duration = duration
        )

        is MobSkillUIModel.Debuff -> MobSkill.Debuff(
            id = id,
            name = name,
            description = description,
            imageName = "",
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            multiplier = multiplier,
            duration = duration
        )
    }
}

fun MobSkill.toUsedInfo(): UsedMobSkillInfo {
    return when (this) {
        is MobSkill.CommonDamage -> UsedMobSkillInfo.CommonDamage(
            refreshTime = refreshTime,
            damage = damage,
            skillId = id
        )

        is MobSkill.DamageOverTime -> UsedMobSkillInfo.DamageOverTime(
            refreshTime = refreshTime,
            damage = damage,
            duration = duration,
            skillId = id
        )

        is MobSkill.Buff -> UsedMobSkillInfo.Buff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration,
            skillId = id
        )

        is MobSkill.Debuff -> UsedMobSkillInfo.Debuff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration,
            skillId = id
        )
    }
}
