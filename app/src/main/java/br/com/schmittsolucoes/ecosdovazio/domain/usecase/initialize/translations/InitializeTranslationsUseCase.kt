package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.translations

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.internacionalization.Language
import br.com.schmittsolucoes.ecosdovazio.domain.model.internacionalization.Translation
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.LanguageRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.TranslationRepository

class InitializeTranslationsUseCase(
    private val languageRepository: LanguageRepository,
    private val translationRepository: TranslationRepository,
    private val languageProvider: LanguageProvider,
) {
    suspend fun executeInternal() {
        val languages = initializeLanguages()
        initializeTranslations(languages)
    }

    private suspend fun initializeLanguages(): List<Language> {
        if (languageRepository.getExistsLanguage()) {
            return emptyList()
        }

        val portuguese = Language(
            id = languageProvider.getTag("pt", "BR"),
            isDefault = false
        )

        val english = Language(
            id = languageProvider.getTag("en", "US"),
            isDefault = true
        )

        val languages = listOf(portuguese, english)
        languageRepository.save(languages)

        return languages
    }

    private suspend fun initializeTranslations(languages: List<Language>) {
        if (!translationRepository.getExistsTranslation()) {
            val identifiers = TranslationIdentifier.entries.map { it.name }
            translationRepository.saveIdentifiers(identifiers)

            val translations = mutableListOf<Translation>()

            languages.forEach { language ->
                TranslationIdentifier.entries.forEach { identifier ->
                    val resourceId = getResourceId(identifier)
                    val text = languageProvider.getString(resourceId, language.id)
                    val translation = Translation(
                        id = identifier,
                        languageId = language.id,
                        translatedText = text
                    )

                    translations.add(translation)
                }
            }

            translationRepository.save(translations)
        }
    }

    private fun getResourceId(identifier: TranslationIdentifier): Int {
        return when (identifier) {
            TranslationIdentifier.WARRIOR_CLASS_NAME -> R.string.warrior_class_name
            TranslationIdentifier.WARRIOR_CLASS_DESCRIPTION -> R.string.warrior_class_description
            TranslationIdentifier.MAGE_CLASS_NAME -> R.string.mage_class_name
            TranslationIdentifier.MAGE_CLASS_DESCRIPTION -> R.string.mage_class_description
            TranslationIdentifier.ARCHER_CLASS_NAME -> R.string.archer_class_name
            TranslationIdentifier.ARCHER_CLASS_DESCRIPTION -> R.string.archer_class_description
            TranslationIdentifier.GUARDIAN_SPEC_NAME -> R.string.guardian_spec_name
            TranslationIdentifier.GUARDIAN_SPEC_DESCRIPTION -> R.string.guardian_spec_description
            TranslationIdentifier.GLADIATOR_SPEC_NAME -> R.string.gladiator_spec_name
            TranslationIdentifier.GLADIATOR_SPEC_DESCRIPTION -> R.string.gladiator_spec_description
            TranslationIdentifier.FIRE_ELEMENTAL_SPEC_NAME -> R.string.fire_elemental_spec_name
            TranslationIdentifier.FIRE_ELEMENTAL_SPEC_DESCRIPTION -> R.string.fire_elemental_spec_description
            TranslationIdentifier.WATER_ELEMENTAL_SPEC_NAME -> R.string.water_elemental_spec_name
            TranslationIdentifier.WATER_ELEMENTAL_SPEC_DESCRIPTION -> R.string.water_elemental_spec_description
            TranslationIdentifier.ENGINEER_SPEC_NAME -> R.string.engineer_spec_name
            TranslationIdentifier.ENGINEER_SPEC_DESCRIPTION -> R.string.engineer_spec_description
            TranslationIdentifier.BEAST_MASTER_SPEC_NAME -> R.string.beast_master_spec_name
            TranslationIdentifier.BEAST_MASTER_SPEC_DESCRIPTION -> R.string.beast_master_spec_description
            TranslationIdentifier.GOBLIN_WARRIOR_MOB_NAME -> R.string.goblin_warrior_mob_name
            TranslationIdentifier.GOBLIN_SHAMAN_MOB_NAME -> R.string.goblin_shaman_mob_name
            TranslationIdentifier.GOBLIN_HEALER_MOB_NAME -> R.string.goblin_healer_mob_name
            TranslationIdentifier.CAVE_ORC_MOB_NAME -> R.string.cave_orc_mob_name
            TranslationIdentifier.FIRST_GUARD_PHASE_NAME -> R.string.first_guard_phase_name
            TranslationIdentifier.MAGIC_AWAKENING_PHASE_NAME -> R.string.magic_awakening_phase_name
            TranslationIdentifier.DOUBLE_PATROL_PHASE_NAME -> R.string.double_patrol_phase_name
            TranslationIdentifier.BLADE_AND_SPELL_PHASE_NAME -> R.string.blade_and_spell_phase_name
            TranslationIdentifier.GOBLIN_AMBUSH_PHASE_NAME -> R.string.goblin_ambush_phase_name
            TranslationIdentifier.TACTICAL_SUPPORT_PHASE_NAME -> R.string.tactical_support_phase_name
            TranslationIdentifier.HEALING_RITUAL_PHASE_NAME -> R.string.healing_ritual_phase_name
            TranslationIdentifier.DARK_VANGUARD_PHASE_NAME -> R.string.dark_vanguard_phase_name
            TranslationIdentifier.IMMORTAL_FRONT_LINE_PHASE_NAME -> R.string.immortal_front_line_phase_name
            TranslationIdentifier.COLOSSUS_AWAKENING_PHASE_NAME -> R.string.colossus_awakening_phase_name
            TranslationIdentifier.GIANT_AND_PRIEST_PHASE_NAME -> R.string.giant_and_priest_phase_name
            TranslationIdentifier.GOBLIN_COVEN_PHASE_NAME -> R.string.goblin_coven_phase_name
            TranslationIdentifier.BALANCED_SQUAD_PHASE_NAME -> R.string.balanced_squad_phase_name
            TranslationIdentifier.BEAST_AND_MINION_PHASE_NAME -> R.string.beast_and_minion_phase_name
            TranslationIdentifier.FULL_BATTALION_PHASE_NAME -> R.string.full_battalion_phase_name
            TranslationIdentifier.BRUTAL_MAGIC_PHASE_NAME -> R.string.brutal_magic_phase_name
            TranslationIdentifier.UNCONTROLLED_FURY_PHASE_NAME -> R.string.uncontrolled_fury_phase_name
            TranslationIdentifier.BOSS_ELITE_PHASE_NAME -> R.string.boss_elite_phase_name
            TranslationIdentifier.RENEWING_TRENCH_PHASE_NAME -> R.string.renewing_trench_phase_name
            TranslationIdentifier.DOUBLE_TREMORS_PHASE_NAME -> R.string.double_tremors_phase_name
            TranslationIdentifier.ARCANE_CONCLAVE_PHASE_NAME -> R.string.arcane_conclave_phase_name
            TranslationIdentifier.HEALER_GUARDIANS_PHASE_NAME -> R.string.healer_guardians_phase_name
            TranslationIdentifier.FORCES_COLLISION_PHASE_NAME -> R.string.forces_collision_phase_name
            TranslationIdentifier.HORDE_AND_FURY_PHASE_NAME -> R.string.horde_and_fury_phase_name
            TranslationIdentifier.IMMORTAL_BEAST_PHASE_NAME -> R.string.immortal_beast_phase_name
            TranslationIdentifier.MYSTIC_STORM_PHASE_NAME -> R.string.mystic_storm_phase_name
            TranslationIdentifier.WALL_OF_FLESH_PHASE_NAME -> R.string.wall_of_flesh_phase_name
            TranslationIdentifier.ULTIMATE_BASTION_PHASE_NAME -> R.string.ultimate_bastion_phase_name
            TranslationIdentifier.ORC_COUNCIL_PHASE_NAME -> R.string.orc_council_phase_name
            TranslationIdentifier.UNSTOPPABLE_TITANS_PHASE_NAME -> R.string.unstoppable_titans_phase_name
            TranslationIdentifier.HEAVY_STRIKE_SKILL_NAME -> R.string.heavy_strike_skill_name
            TranslationIdentifier.HEAVY_STRIKE_SKILL_DESCRIPTION -> R.string.heavy_strike_skill_description
            TranslationIdentifier.TACTICAL_ADVANCE_SKILL_NAME -> R.string.tactical_advance_skill_name
            TranslationIdentifier.TACTICAL_ADVANCE_SKILL_DESCRIPTION -> R.string.tactical_advance_skill_description
            TranslationIdentifier.DEFENSIVE_STANCE_SKILL_NAME -> R.string.defensive_stance_skill_name
            TranslationIdentifier.DEFENSIVE_STANCE_SKILL_DESCRIPTION -> R.string.defensive_stance_skill_description
            TranslationIdentifier.BLOODY_STRIKE_SKILL_NAME -> R.string.bloody_strike_skill_name
            TranslationIdentifier.BLOODY_STRIKE_SKILL_DESCRIPTION -> R.string.bloody_strike_skill_description
            TranslationIdentifier.BATTLE_FURY_SKILL_NAME -> R.string.battle_fury_skill_name
            TranslationIdentifier.BATTLE_FURY_SKILL_DESCRIPTION -> R.string.battle_fury_skill_description
            TranslationIdentifier.RELENTLESS_CHARGE_SKILL_NAME -> R.string.relentless_charge_skill_name
            TranslationIdentifier.RELENTLESS_CHARGE_SKILL_DESCRIPTION -> R.string.relentless_charge_skill_description
            TranslationIdentifier.BRUTAL_RIFT_SKILL_NAME -> R.string.brutal_rift_skill_name
            TranslationIdentifier.BRUTAL_RIFT_SKILL_DESCRIPTION -> R.string.brutal_rift_skill_description
            TranslationIdentifier.BREAK_ARMOR_SKILL_NAME -> R.string.break_armor_skill_name
            TranslationIdentifier.BREAK_ARMOR_SKILL_DESCRIPTION -> R.string.break_armor_skill_description
            TranslationIdentifier.ARCANE_MISSILE_SKILL_NAME -> R.string.arcane_missile_skill_name
            TranslationIdentifier.ARCANE_MISSILE_SKILL_DESCRIPTION -> R.string.arcane_missile_skill_description
            TranslationIdentifier.QUICK_FREEZE_SKILL_NAME -> R.string.quick_freeze_skill_name
            TranslationIdentifier.QUICK_FREEZE_SKILL_DESCRIPTION -> R.string.quick_freeze_skill_description
            TranslationIdentifier.MANA_BARRIER_SKILL_NAME -> R.string.mana_barrier_skill_name
            TranslationIdentifier.MANA_BARRIER_SKILL_DESCRIPTION -> R.string.mana_barrier_skill_description
            TranslationIdentifier.FLAMING_TOUCH_SKILL_NAME -> R.string.flaming_touch_skill_name
            TranslationIdentifier.FLAMING_TOUCH_SKILL_DESCRIPTION -> R.string.flaming_touch_skill_description
            TranslationIdentifier.SHOCK_WAVE_SKILL_NAME -> R.string.shock_wave_skill_name
            TranslationIdentifier.SHOCK_WAVE_SKILL_DESCRIPTION -> R.string.shock_wave_skill_description
            TranslationIdentifier.ARCANE_FOCUS_SKILL_NAME -> R.string.arcane_focus_skill_name
            TranslationIdentifier.ARCANE_FOCUS_SKILL_DESCRIPTION -> R.string.arcane_focus_skill_description
            TranslationIdentifier.DRAIN_ESSENCE_SKILL_NAME -> R.string.drain_essence_skill_name
            TranslationIdentifier.DRAIN_ESSENCE_SKILL_DESCRIPTION -> R.string.drain_essence_skill_description
            TranslationIdentifier.METEOR_SHOWER_SKILL_NAME -> R.string.meteor_shower_skill_name
            TranslationIdentifier.METEOR_SHOWER_SKILL_DESCRIPTION -> R.string.meteor_shower_skill_description
            TranslationIdentifier.PRECISION_SHOT_SKILL_NAME -> R.string.precision_shot_skill_name
            TranslationIdentifier.PRECISION_SHOT_SKILL_DESCRIPTION -> R.string.precision_shot_skill_description
            TranslationIdentifier.CORROSIVE_ARROWS_SKILL_NAME -> R.string.corrosive_arrows_skill_name
            TranslationIdentifier.CORROSIVE_ARROWS_SKILL_DESCRIPTION -> R.string.corrosive_arrows_skill_description
            TranslationIdentifier.TACTICAL_RETREAT_SKILL_NAME -> R.string.tactical_retreat_skill_name
            TranslationIdentifier.TACTICAL_RETREAT_SKILL_DESCRIPTION -> R.string.tactical_retreat_skill_description
            TranslationIdentifier.THORN_TRAP_SKILL_NAME -> R.string.thorn_trap_skill_name
            TranslationIdentifier.THORN_TRAP_SKILL_DESCRIPTION -> R.string.thorn_trap_skill_description
            TranslationIdentifier.POISON_ARROW_SKILL_NAME -> R.string.poison_arrow_skill_name
            TranslationIdentifier.POISON_ARROW_SKILL_DESCRIPTION -> R.string.poison_arrow_skill_description
            TranslationIdentifier.PERFECT_AIM_SKILL_NAME -> R.string.perfect_aim_skill_name
            TranslationIdentifier.PERFECT_AIM_SKILL_DESCRIPTION -> R.string.perfect_aim_skill_description
            TranslationIdentifier.SMOKE_SCREEN_SKILL_NAME -> R.string.smoke_screen_skill_name
            TranslationIdentifier.SMOKE_SCREEN_SKILL_DESCRIPTION -> R.string.smoke_screen_skill_description
            TranslationIdentifier.PIERCING_SHOT_SKILL_NAME -> R.string.piercing_shot_skill_name
            TranslationIdentifier.PIERCING_SHOT_SKILL_DESCRIPTION -> R.string.piercing_shot_skill_description
            TranslationIdentifier.HOLY_SHIELD_SKILL_NAME -> R.string.holy_shield_skill_name
            TranslationIdentifier.HOLY_SHIELD_SKILL_DESCRIPTION -> R.string.holy_shield_skill_description
            TranslationIdentifier.COUNTERATTACK_SKILL_NAME -> R.string.counterattack_skill_name
            TranslationIdentifier.COUNTERATTACK_SKILL_DESCRIPTION -> R.string.counterattack_skill_description
            TranslationIdentifier.BASTION_SKILL_NAME -> R.string.bastion_skill_name
            TranslationIdentifier.BASTION_SKILL_DESCRIPTION -> R.string.bastion_skill_description
            TranslationIdentifier.BRUTAL_CUT_SKILL_NAME -> R.string.brutal_cut_skill_name
            TranslationIdentifier.BRUTAL_CUT_SKILL_DESCRIPTION -> R.string.brutal_cut_skill_description
            TranslationIdentifier.BLOODY_FURY_SKILL_NAME -> R.string.bloody_fury_skill_name
            TranslationIdentifier.BLOODY_FURY_SKILL_DESCRIPTION -> R.string.bloody_fury_skill_description
            TranslationIdentifier.WHIRLWIND_SKILL_NAME -> R.string.whirlwind_skill_name
            TranslationIdentifier.WHIRLWIND_SKILL_DESCRIPTION -> R.string.whirlwind_skill_description
            TranslationIdentifier.FIREBALL_SKILL_NAME -> R.string.fireball_skill_name
            TranslationIdentifier.FIREBALL_SKILL_DESCRIPTION -> R.string.fireball_skill_description
            TranslationIdentifier.IGNEOUS_EXPLOSION_SKILL_NAME -> R.string.igneous_explosion_skill_name
            TranslationIdentifier.IGNEOUS_EXPLOSION_SKILL_DESCRIPTION -> R.string.igneous_explosion_skill_description
            TranslationIdentifier.FIRE_SKIN_SKILL_NAME -> R.string.fire_skin_skill_name
            TranslationIdentifier.FIRE_SKIN_SKILL_DESCRIPTION -> R.string.fire_skin_skill_description
            TranslationIdentifier.ICE_SPEAR_SKILL_NAME -> R.string.ice_spear_skill_name
            TranslationIdentifier.ICE_SPEAR_SKILL_DESCRIPTION -> R.string.ice_spear_skill_description
            TranslationIdentifier.INVIGORATING_HEAL_SKILL_NAME -> R.string.invigorating_heal_skill_name
            TranslationIdentifier.INVIGORATING_HEAL_SKILL_DESCRIPTION -> R.string.invigorating_heal_skill_description
            TranslationIdentifier.BLIZZARD_SKILL_NAME -> R.string.blizzard_skill_name
            TranslationIdentifier.BLIZZARD_SKILL_DESCRIPTION -> R.string.blizzard_skill_description
            TranslationIdentifier.CANNON_SHOT_SKILL_NAME -> R.string.cannon_shot_skill_name
            TranslationIdentifier.CANNON_SHOT_SKILL_DESCRIPTION -> R.string.cannon_shot_skill_description
            TranslationIdentifier.AUTO_TURRET_SKILL_NAME -> R.string.auto_turret_skill_name
            TranslationIdentifier.AUTO_TURRET_SKILL_DESCRIPTION -> R.string.auto_turret_skill_description
            TranslationIdentifier.FRAG_GRENADE_SKILL_NAME -> R.string.frag_grenade_skill_name
            TranslationIdentifier.FRAG_GRENADE_SKILL_DESCRIPTION -> R.string.frag_grenade_skill_description
            TranslationIdentifier.PRECISION_FIRE_SKILL_NAME -> R.string.precision_fire_skill_name
            TranslationIdentifier.PRECISION_FIRE_SKILL_DESCRIPTION -> R.string.precision_fire_skill_description
            TranslationIdentifier.EAGLE_EYE_SKILL_NAME -> R.string.eagle_eye_skill_name
            TranslationIdentifier.EAGLE_EYE_SKILL_DESCRIPTION -> R.string.eagle_eye_skill_description
            TranslationIdentifier.GROUND_TRAP_SKILL_NAME -> R.string.ground_trap_skill_name
            TranslationIdentifier.GROUND_TRAP_SKILL_DESCRIPTION -> R.string.ground_trap_skill_description
            TranslationIdentifier.QUICK_ATTACK_SKILL_NAME -> R.string.quick_attack_skill_name
            TranslationIdentifier.QUICK_ATTACK_SKILL_DESCRIPTION -> R.string.quick_attack_skill_description
            TranslationIdentifier.WILD_INSTINCT_SKILL_NAME -> R.string.wild_instinct_skill_name
            TranslationIdentifier.WILD_INSTINCT_SKILL_DESCRIPTION -> R.string.wild_instinct_skill_description
            TranslationIdentifier.CHARGE_SKILL_NAME -> R.string.charge_skill_name
            TranslationIdentifier.CHARGE_SKILL_DESCRIPTION -> R.string.charge_skill_description
            TranslationIdentifier.GOBLIN_FURY_SKILL_NAME -> R.string.goblin_fury_skill_name
            TranslationIdentifier.GOBLIN_FURY_SKILL_DESCRIPTION -> R.string.goblin_fury_skill_description
            TranslationIdentifier.MYSTIC_PROJECTILE_SKILL_NAME -> R.string.mystic_projectile_skill_name
            TranslationIdentifier.MYSTIC_PROJECTILE_SKILL_DESCRIPTION -> R.string.mystic_projectile_skill_description
            TranslationIdentifier.CURSE_SKILL_NAME -> R.string.curse_skill_name
            TranslationIdentifier.CURSE_SKILL_DESCRIPTION -> R.string.curse_skill_description
            TranslationIdentifier.ENERGY_SHIELD_SKILL_NAME -> R.string.energy_shield_skill_name
            TranslationIdentifier.ENERGY_SHIELD_SKILL_DESCRIPTION -> R.string.energy_shield_skill_description
            TranslationIdentifier.MANA_DRAIN_SKILL_NAME -> R.string.mana_drain_skill_name
            TranslationIdentifier.MANA_DRAIN_SKILL_DESCRIPTION -> R.string.mana_drain_skill_description
            TranslationIdentifier.ARCANE_EXPLOSION_SKILL_NAME -> R.string.arcane_explosion_skill_name
            TranslationIdentifier.ARCANE_EXPLOSION_SKILL_DESCRIPTION -> R.string.arcane_explosion_skill_description
            TranslationIdentifier.HEALING_TOUCH_SKILL_NAME -> R.string.healing_touch_skill_name
            TranslationIdentifier.HEALING_TOUCH_SKILL_DESCRIPTION -> R.string.healing_touch_skill_description
            TranslationIdentifier.REGENERATION_SKILL_NAME -> R.string.regeneration_skill_name
            TranslationIdentifier.REGENERATION_SKILL_DESCRIPTION -> R.string.regeneration_skill_description
            TranslationIdentifier.GIANT_SLAP_SKILL_NAME -> R.string.giant_slap_skill_name
            TranslationIdentifier.GIANT_SLAP_SKILL_DESCRIPTION -> R.string.giant_slap_skill_description
            TranslationIdentifier.THICK_SKIN_SKILL_NAME -> R.string.thick_skin_skill_name
            TranslationIdentifier.THICK_SKIN_SKILL_DESCRIPTION -> R.string.thick_skin_skill_description
            TranslationIdentifier.SMASH_SKILL_NAME -> R.string.smash_skill_name
            TranslationIdentifier.SMASH_SKILL_DESCRIPTION -> R.string.smash_skill_description
            TranslationIdentifier.THREATENING_ROAR_SKILL_NAME -> R.string.threatening_roar_skill_name
            TranslationIdentifier.THREATENING_ROAR_SKILL_DESCRIPTION -> R.string.threatening_roar_skill_description
            TranslationIdentifier.BOSS_STRIKE_SKILL_NAME -> R.string.boss_strike_skill_name
            TranslationIdentifier.BOSS_STRIKE_SKILL_DESCRIPTION -> R.string.boss_strike_skill_description
        }
    }
}