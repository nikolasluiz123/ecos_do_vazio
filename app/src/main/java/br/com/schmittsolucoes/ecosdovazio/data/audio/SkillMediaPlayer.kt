package br.com.schmittsolucoes.ecosdovazio.data.audio

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.audio.AudioPlayer
import br.com.schmittsolucoes.ecosdovazio.domain.audio.SkillAudioPlayer
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import javax.inject.Inject

class SkillMediaPlayer @Inject constructor(
    private val audioPlayer: AudioPlayer
) : SkillAudioPlayer {

    override suspend fun playSkillSound(identifier: TranslationIdentifier) {
        val soundResId = when (identifier) {
            TranslationIdentifier.HEAVY_STRIKE_SKILL_NAME -> R.raw.skill_golpe_pesado
            TranslationIdentifier.TACTICAL_ADVANCE_SKILL_NAME -> R.raw.skill_avanco_tatico
            TranslationIdentifier.DEFENSIVE_STANCE_SKILL_NAME -> R.raw.skill_postura_defensiva
            TranslationIdentifier.BLOODY_STRIKE_SKILL_NAME -> R.raw.skill_golpe_sangrento
            TranslationIdentifier.BATTLE_FURY_SKILL_NAME -> R.raw.skill_furia_de_batalha
            TranslationIdentifier.RELENTLESS_CHARGE_SKILL_NAME -> R.raw.skill_investida_implacavel
            TranslationIdentifier.BRUTAL_RIFT_SKILL_NAME -> R.raw.skill_fenda_brutal
            TranslationIdentifier.BREAK_ARMOR_SKILL_NAME -> R.raw.skill_quebrar_armadura
            TranslationIdentifier.ARCANE_MISSILE_SKILL_NAME -> R.raw.skill_missil_arcano
            TranslationIdentifier.QUICK_FREEZE_SKILL_NAME -> R.raw.skill_congelamento_rapido
            TranslationIdentifier.MANA_BARRIER_SKILL_NAME -> R.raw.skill_barreira_de_mana
            TranslationIdentifier.FLAMING_TOUCH_SKILL_NAME -> R.raw.skill_toque_flamejante
            TranslationIdentifier.SHOCK_WAVE_SKILL_NAME -> R.raw.skill_onda_de_choque
            TranslationIdentifier.DRAIN_ESSENCE_SKILL_NAME -> R.raw.skill_drenar_essencia
            TranslationIdentifier.METEOR_SHOWER_SKILL_NAME -> R.raw.skill_chuva_de_meteoros
            TranslationIdentifier.PRECISION_SHOT_SKILL_NAME -> R.raw.skill_tiro_preciso
            TranslationIdentifier.CORROSIVE_ARROWS_SKILL_NAME -> R.raw.skill_flechas_corrosivas
            TranslationIdentifier.TACTICAL_RETREAT_SKILL_NAME -> R.raw.skill_recuo_tatico
            TranslationIdentifier.THORN_TRAP_SKILL_NAME -> R.raw.skill_armadilha_de_espinhos
            TranslationIdentifier.POISON_ARROW_SKILL_NAME -> R.raw.skill_flecha_envenenada
            TranslationIdentifier.PERFECT_AIM_SKILL_NAME -> R.raw.skill_mira_perfeita
            TranslationIdentifier.SMOKE_SCREEN_SKILL_NAME -> R.raw.skill_cortina_de_fumaca
            TranslationIdentifier.PIERCING_SHOT_SKILL_NAME -> R.raw.skill_disparo_perfurante
            TranslationIdentifier.HOLY_SHIELD_SKILL_NAME -> R.raw.skill_escudo_sagrado
            TranslationIdentifier.BRUTAL_CUT_SKILL_NAME -> R.raw.skill_corte_brutal
            TranslationIdentifier.WHIRLWIND_SKILL_NAME -> R.raw.skill_redemoinho
            TranslationIdentifier.FIRE_SKIN_SKILL_NAME -> R.raw.skill_pele_de_fogo
            TranslationIdentifier.IGNEOUS_EXPLOSION_SKILL_NAME -> R.raw.skill_explosao_ignea
            TranslationIdentifier.INVIGORATING_HEAL_SKILL_NAME -> R.raw.skill_cura_revigorante
            TranslationIdentifier.BLIZZARD_SKILL_NAME -> R.raw.skill_nevasca
            TranslationIdentifier.CANNON_SHOT_SKILL_NAME -> R.raw.skill_tiro_de_canhao
            TranslationIdentifier.AUTO_TURRET_SKILL_NAME -> R.raw.skill_torreta_automatica
            TranslationIdentifier.FRAG_GRENADE_SKILL_NAME -> R.raw.skill_granada_de_fragmentacao
            TranslationIdentifier.GROUND_TRAP_SKILL_NAME -> R.raw.skill_armadilha_de_solo
            TranslationIdentifier.QUICK_ATTACK_SKILL_NAME -> R.raw.skill_ataque_rapido
            TranslationIdentifier.WILD_INSTINCT_SKILL_NAME -> R.raw.skill_instinto_selvagem
            TranslationIdentifier.CHARGE_SKILL_NAME -> R.raw.skill_investida
            TranslationIdentifier.MYSTIC_PROJECTILE_SKILL_NAME -> R.raw.skill_projetil_mistico
            TranslationIdentifier.CURSE_SKILL_NAME -> R.raw.skill_maldicao
            TranslationIdentifier.ENERGY_SHIELD_SKILL_NAME -> R.raw.skill_escudo_de_energia
            TranslationIdentifier.MANA_DRAIN_SKILL_NAME -> R.raw.skill_dreno_de_mana
            TranslationIdentifier.ARCANE_EXPLOSION_SKILL_NAME -> R.raw.skill_explosao_arcana
            TranslationIdentifier.HEALING_TOUCH_SKILL_NAME -> R.raw.skill_toque_de_cura
            TranslationIdentifier.REGENERATION_SKILL_NAME -> R.raw.skill_regeneracao
            TranslationIdentifier.GIANT_SLAP_SKILL_NAME -> R.raw.skill_pancada_gigante
            TranslationIdentifier.THICK_SKIN_SKILL_NAME -> R.raw.skill_pele_grossa
            TranslationIdentifier.SMASH_SKILL_NAME -> R.raw.skill_esmagar
            TranslationIdentifier.THREATENING_ROAR_SKILL_NAME -> R.raw.skill_rugido_ameacador
            else -> null
        }

        soundResId?.let {
            audioPlayer.playSound(it)
        }
    }
}
