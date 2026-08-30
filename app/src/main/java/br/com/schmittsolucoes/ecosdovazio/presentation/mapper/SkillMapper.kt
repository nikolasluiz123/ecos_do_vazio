package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.core.formatters.NumberFormatter
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkillDetails
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.IdentifiedSkillAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel
import javax.inject.Inject

class SkillMapper @Inject constructor(
    private val numberFormatter: NumberFormatter,
    private val resourcesProvider: ResourcesProvider
) {

    fun mapToUIModel(skill: CharSkillDetails): CharSkillDetailsUIModel {
        return CharSkillDetailsUIModel(
            id = skill.id,
            name = skill.name,
            description = formatDescription(
                description = skill.description,
                category = skill.skillCategory,
                damage = skill.damage,
                multiplier = skill.multiplier,
                duration = skill.duration,
                refreshTime = skill.refreshTime
            ),
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
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    damage = skill.damage,
                    refreshTime = skill.refreshTime
                ),
                refreshTime = skill.refreshTime,
                minLevel = skill.minLevel,
                image = image,
                attributes = skill.attributes,
                currentRefreshTime = currentRefreshTime,
                blocked = blocked,
                damage = skill.damage
            )

            is CharSkill.AreaDamage -> CharSkillUIModel.AreaDamage(
                id = skill.id,
                name = skill.name,
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    damage = skill.damage,
                    refreshTime = skill.refreshTime
                ),
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
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    damage = skill.damage,
                    duration = skill.duration,
                    refreshTime = skill.refreshTime
                ),
                refreshTime = skill.refreshTime,
                minLevel = skill.minLevel,
                image = image,
                attributes = skill.attributes,
                currentRefreshTime = currentRefreshTime,
                blocked = blocked,
                damage = skill.damage,
                duration = skill.duration
            )

            is CharSkill.VampiricDamage -> CharSkillUIModel.VampiricDamage(
                id = skill.id,
                name = skill.name,
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    damage = skill.damage,
                    multiplier = skill.multiplier,
                    refreshTime = skill.refreshTime
                ),
                refreshTime = skill.refreshTime,
                minLevel = skill.minLevel,
                image = image,
                attributes = skill.attributes,
                currentRefreshTime = currentRefreshTime,
                blocked = blocked,
                damage = skill.damage,
                multiplier = skill.multiplier
            )

            is CharSkill.Buff -> CharSkillUIModel.Buff(
                id = skill.id,
                name = skill.name,
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    multiplier = skill.multiplier,
                    duration = skill.duration,
                    refreshTime = skill.refreshTime
                ),
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
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    damage = skill.damage,
                    multiplier = skill.multiplier,
                    duration = skill.duration,
                    refreshTime = skill.refreshTime
                ),
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
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    damage = skill.damage,
                    refreshTime = skill.refreshTime
                ),
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
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    damage = skill.damage,
                    duration = skill.duration,
                    refreshTime = skill.refreshTime
                ),
                image = image,
                refreshTime = skill.refreshTime,
                currentRefreshTime = skill.currentRefreshTime,
                blocked = skill.blocked,
                minLevel = skill.minLevel,
                damage = skill.damage,
                duration = skill.duration
            )

            is MobSkill.VampiricDamage -> MobSkillUIModel.VampiricDamage(
                id = skill.id,
                name = skill.name,
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    damage = skill.damage,
                    multiplier = skill.multiplier,
                    refreshTime = skill.refreshTime
                ),
                image = image,
                refreshTime = skill.refreshTime,
                currentRefreshTime = skill.currentRefreshTime,
                blocked = skill.blocked,
                minLevel = skill.minLevel,
                damage = skill.damage,
                multiplier = skill.multiplier
            )

            is MobSkill.Buff -> MobSkillUIModel.Buff(
                id = skill.id,
                name = skill.name,
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    multiplier = skill.multiplier,
                    duration = skill.duration,
                    refreshTime = skill.refreshTime
                ),
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
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    multiplier = skill.multiplier,
                    duration = skill.duration,
                    refreshTime = skill.refreshTime
                ),
                image = image,
                skillCategory = skill.skillCategory,
                refreshTime = skill.refreshTime,
                currentRefreshTime = skill.currentRefreshTime,
                blocked = skill.blocked,
                minLevel = skill.minLevel,
                multiplier = skill.multiplier,
                duration = skill.duration
            )

            is MobSkill.Heal -> MobSkillUIModel.Heal(
                id = skill.id,
                name = skill.name,
                description = formatDescription(
                    description = skill.description,
                    category = skill.skillCategory,
                    refreshTime = skill.refreshTime,
                    lifeRestore = skill.lifeRestore
                ),
                image = image,
                refreshTime = skill.refreshTime,
                currentRefreshTime = skill.currentRefreshTime,
                blocked = skill.blocked,
                minLevel = skill.minLevel,
                skillCategory = skill.skillCategory,
                lifeRestore = skill.lifeRestore
            )
        }
    }

    fun mapToDomain(skillUIModel: MobSkillUIModel): MobSkill {
        return when (skillUIModel) {
            is MobSkillUIModel.CommonDamage -> MobSkill.CommonDamage(
                id = skillUIModel.id,
                name = skillUIModel.name,
                description = skillUIModel.description,
                imageName = "",
                refreshTime = skillUIModel.refreshTime,
                currentRefreshTime = skillUIModel.currentRefreshTime,
                blocked = skillUIModel.blocked,
                minLevel = skillUIModel.minLevel,
                damage = skillUIModel.damage
            )

            is MobSkillUIModel.DamageOverTime -> MobSkill.DamageOverTime(
                id = skillUIModel.id,
                name = skillUIModel.name,
                description = skillUIModel.description,
                imageName = "",
                refreshTime = skillUIModel.refreshTime,
                currentRefreshTime = skillUIModel.currentRefreshTime,
                blocked = skillUIModel.blocked,
                minLevel = skillUIModel.minLevel,
                damage = skillUIModel.damage,
                duration = skillUIModel.duration
            )

            is MobSkillUIModel.VampiricDamage -> MobSkill.VampiricDamage(
                id = skillUIModel.id,
                name = skillUIModel.name,
                description = skillUIModel.description,
                imageName = "",
                refreshTime = skillUIModel.refreshTime,
                currentRefreshTime = skillUIModel.currentRefreshTime,
                blocked = skillUIModel.blocked,
                minLevel = skillUIModel.minLevel,
                damage = skillUIModel.damage,
                multiplier = skillUIModel.multiplier
            )

            is MobSkillUIModel.Buff -> MobSkill.Buff(
                id = skillUIModel.id,
                name = skillUIModel.name,
                description = skillUIModel.description,
                imageName = "",
                skillCategory = skillUIModel.skillCategory,
                refreshTime = skillUIModel.refreshTime,
                currentRefreshTime = skillUIModel.currentRefreshTime,
                blocked = skillUIModel.blocked,
                minLevel = skillUIModel.minLevel,
                multiplier = skillUIModel.multiplier,
                duration = skillUIModel.duration
            )

            is MobSkillUIModel.Debuff -> MobSkill.Debuff(
                id = skillUIModel.id,
                name = skillUIModel.name,
                description = skillUIModel.description,
                imageName = "",
                skillCategory = skillUIModel.skillCategory,
                refreshTime = skillUIModel.refreshTime,
                currentRefreshTime = skillUIModel.currentRefreshTime,
                blocked = skillUIModel.blocked,
                minLevel = skillUIModel.minLevel,
                multiplier = skillUIModel.multiplier,
                duration = skillUIModel.duration
            )

            is MobSkillUIModel.Heal -> MobSkill.Heal(
                id = skillUIModel.id,
                name = skillUIModel.name,
                description = skillUIModel.description,
                imageName = "",
                refreshTime = skillUIModel.refreshTime,
                currentRefreshTime = skillUIModel.currentRefreshTime,
                blocked = skillUIModel.blocked,
                minLevel = skillUIModel.minLevel,
                skillCategory = skillUIModel.skillCategory,
                lifeRestore = skillUIModel.lifeRestore
            )
        }
    }

    private fun formatDescription(
        description: String,
        category: SkillCategory,
        damage: Long? = null,
        multiplier: Double? = null,
        duration: Int? = null,
        refreshTime: Int,
        lifeRestore: Long? = null
    ): String {
        val multiplierStr = multiplier?.let { numberFormatter.formatPercentage(it) } ?: ""

        return try {
            when (category) {
                SkillCategory.DAMAGE, SkillCategory.AREA_DAMAGE -> {
                    description.format(damage ?: 0L, refreshTime)
                }

                SkillCategory.DAMAGE_OVER_TIME -> {
                    description.format(damage ?: 0L, duration ?: 0, refreshTime)
                }

                SkillCategory.VAMPIRIC_DAMAGE -> {
                    description.format(damage ?: 0L, multiplierStr, refreshTime)
                }

                SkillCategory.OFFENSIVE_BUFF, SkillCategory.DEFENSIVE_BUFF -> {
                    description.format(multiplierStr, duration ?: 0, refreshTime)
                }

                SkillCategory.OFFENSIVE_DEBUFF, SkillCategory.DEFENSIVE_DEBUFF -> {
                    description.format(damage ?: 0L, multiplierStr, duration ?: 0, refreshTime)
                }

                SkillCategory.HEAL, SkillCategory.AREA_HEAL -> {
                    description.format(lifeRestore ?: 0L, refreshTime)
                }
            }
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

