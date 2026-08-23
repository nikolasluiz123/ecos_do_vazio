package br.com.schmittsolucoes.ecosdovazio.data.provider

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import javax.inject.Inject

const val WARRIOR_CLASS_IMAGE_KEY = "classe_guerreiro"
const val MAGE_CLASS_IMAGE_KEY = "classe_mago"
const val ARCHER_CLASS_IMAGE_KEY = "classe_arqueiro"

const val BATTLE_IMAGE_WARRIOR_CLASS_IMAGE_KEY = "classe_guerreiro_16_9"
const val BATTLE_IMAGE_MAGE_CLASS_IMAGE_KEY = "classe_mago_16_9"
const val BATTLE_IMAGE_ARCHER_CLASS_IMAGE_KEY = "classe_arqueiro_16_9"

const val WARRIOR_CLASS_PROFILE_IMAGE_KEY = "classe_guerreiro_perfil"
const val MAGE_CLASS_PROFILE_IMAGE_KEY = "classe_mago_perfil"
const val ARCHER_CLASS_PROFILE_IMAGE_KEY = "classe_arqueiro_perfil"

const val BATTLE_IMAGE_GOBLIN_HEALER_KEY = "goblin_curandeiro_16_9"
const val BATTLE_IMAGE_GOBLIN_WARRIOR_KEY = "goblin_guerreiro_16_9"
const val BATTLE_IMAGE_GOBLIN_XAMA_KEY = "goblin_xama_16_9"
const val BATTLE_IMAGE_ORC_KEY = "orc_das_cavernas_16_9"

const val GUARDIAN_SPECIALIZATION_IMAGE_KEY = "especializacao_guardiao"
const val GLADIATOR_SPECIALIZATION_IMAGE_KEY = "especializacao_gladiador"
const val WATER_MAGE_SPECIALIZATION_IMAGE_KEY = "especializacao_mago_agua"
const val FIRE_MAGE_SPECIALIZATION_IMAGE_KEY = "especializacao_mago_fogo"
const val ENGINEER_SPECIALIZATION_IMAGE_KEY = "especializacao_engenheiro"
const val BEASTMASTER_SPECIALIZATION_IMAGE_KEY = "especializacao_mestre_feras"

const val BATTLE_IMAGE_GUARDIAN_SPECIALIZATION_IMAGE_KEY = "especializacao_guardiao_16_9"
const val BATTLE_IMAGE_GLADIATOR_SPECIALIZATION_IMAGE_KEY = "especializacao_gladiador_16_9"
const val BATTLE_IMAGE_WATER_MAGE_SPECIALIZATION_IMAGE_KEY = "especializacao_mago_agua_16_9"
const val BATTLE_IMAGE_FIRE_MAGE_SPECIALIZATION_IMAGE_KEY = "especializacao_mago_fogo_16_9"
const val BATTLE_IMAGE_ENGINEER_SPECIALIZATION_IMAGE_KEY = "especializacao_engenheiro_16_9"
const val BATTLE_IMAGE_BEASTMASTER_SPECIALIZATION_IMAGE_KEY = "especializacao_mestre_feras_16_9"

const val GUARDIAN_SPECIALIZATION_PROFILE_IMAGE_KEY = "especializacao_guardiao_perfil"
const val GLADIATOR_SPECIALIZATION_PROFILE_IMAGE_KEY = "especializacao_gladiador_perfil"
const val WATER_MAGE_SPECIALIZATION_PROFILE_IMAGE_KEY = "especializacao_mago_agua_perfil"
const val FIRE_MAGE_SPECIALIZATION_PROFILE_IMAGE_KEY = "especializacao_mago_fogo_perfil"
const val ENGINEER_SPECIALIZATION_PROFILE_IMAGE_KEY = "especializacao_engenheiro_perfil"
const val BEASTMASTER_SPECIALIZATION_PROFILE_IMAGE_KEY = "especializacao_mestre_feras_perfil"

const val SKILL_BASTION_KEY = "skill_bastiao"
const val SKILL_BLIZZARD_KEY = "skill_nevasca"
const val SKILL_WHIRLWIND_KEY = "skill_redemoinho"
const val SKILL_ARCANE_FOCUS_KEY = "skill_foco_arcano"
const val SKILL_FIREBALL_KEY = "skill_bola_de_fogo"
const val SKILL_BRUTAL_SLASH_KEY = "skill_corte_brutal"
const val SKILL_BRUTAL_RIFT_KEY = "skill_fenda_brutal"
const val SKILL_HEAVY_STRIKE_KEY = "skill_golpe_pesado"
const val SKILL_FOCUS_SKIN_KEY = "skill_pele_de_foco"
const val SKILL_TACTICAL_RETREAT_KEY = "skill_recuo_tatico"
const val SKILL_PRECISE_SHOT_KEY = "skill_tiro_preciso"
const val SKILL_TACTICAL_ADVANCE_KEY = "skill_avanco_tatico"
const val SKILL_COUNTER_ATTACK_KEY = "skill_contra_ataque"
const val SKILL_ICE_LANCE_KEY = "skill_lanca_de_gelo"
const val SKILL_PERFECT_AIM_KEY = "skill_mira_perfeita"
const val SKILL_ARCANE_MISSILE_KEY = "skill_missil_arcano"
const val SKILL_RAPID_FIRE_KEY = "skill_disparo_rapido"
const val SKILL_HOLY_SHIELD_KEY = "skill_escudo_sagrado"
const val SKILL_FIRE_BLAST_KEY = "skill_explosao_ignea"
const val SKILL_EAGLE_EYE_KEY = "skill_olhar_de_aguia"
const val SKILL_SHOCKWAVE_KEY = "skill_onda_de_choque"
const val SKILL_CANNON_SHOT_KEY = "skill_tiro_de_canhao"
const val SKILL_ESSENCE_DRAIN_KEY = "skill_drenar_essencia"
const val SKILL_BLOOD_RAGE_KEY = "skill_furia_sangrenta"
const val SKILL_BLOOD_STRIKE_KEY = "skill_golpe_sangrento"
const val SKILL_INVIGORATING_HEAL_KEY = "skill_cura_revigorante"
const val SKILL_BATTLE_RAGE_KEY = "skill_furia_de_batalha"
const val SKILL_FLAMING_TOUCH_KEY = "skill_toque_flamejante"
const val SKILL_GROUND_TRAP_KEY = "skill_armadilha_de_solo"
const val SKILL_METEOR_SHOWER_KEY = "skill_chuva_de_meteoros"
const val SKILL_SMOKESCREEN_KEY = "skill_cortina_de_fumaca"
const val SKILL_POISON_ARROW_KEY = "skill_flecha_envenenada"
const val SKILL_DEFENSIVE_STANCE_KEY = "skill_postura_defensiva"
const val SKILL_ARMOR_BREAK_KEY = "skill_quebrar_armaduras"
const val SKILL_PIERCING_SHOT_KEY = "skill_disparo_perfurante"
const val SKILL_AUTOMATIC_TURRET_KEY = "skill_torreta_automatica"
const val SKILL_FLASH_FREEZE_KEY = "skill_congelamento_rapido"
const val SKILL_PRECISION_SHOT_KEY = "skill_disparo_de_precisao"
const val SKILL_RELENTLESS_CHARGE_KEY = "skill_investida_implacavel"
const val SKILL_THORN_TRAP_KEY = "skill_armadilha_de_espinhos"
const val SKILL_FRAGMENTATION_GRENADE_KEY = "skill_granada_de_fragmentacao"
const val SKILL_MANA_BARRIER = "skill_barreira_de_mana"

const val PHASE_PURPLE_FLAME_ICON_KEY = "icone_fase_chama_roxa"
const val PHASE_CRACKED_SHIELD_ICON_KEY = "icone_fase_escudo_rachado"
const val PHASE_CHIPPED_SWORD_ICON_KEY = "icone_fase_espada_lascada"
const val PHASE_GIANT_CLUB_ICON_KEY = "icone_fase_porrete_gigante"
const val PHASE_HEART_WITH_AURA_ICON_KEY = "icone_fase_coracao_com_aura"

const val MAGE_COAT_ARMS_IMAGE_KEY = "brasao_mago"
const val ARCHER_COAT_ARMS_IMAGE_KEY = "brasao_arqueiro"
const val GUARDIAN_COAT_ARMS_IMAGE_KEY = "brasao_guardiao"
const val GLADIATOR_COAT_ARMS_IMAGE_KEY = "brasao_gladiador"
const val WARRIOR_COAT_ARMS_IMAGE_KEY = "brasao_guerreiro"
const val ENGINEER_COAT_ARMS_IMAGE_KEY = "brasao_engenheiro"
const val WATER_MAGE_COAT_ARMS_IMAGE_KEY = "brasao_mago_de_agua"
const val FIRE_MAGE_COAT_ARMS_IMAGE_KEY = "brasao_mago_de_fogo"
const val BEASTMASTER_COAT_ARMS_IMAGE_KEY = "brasao_mestre_das_feras"

class AndroidResourcesProvider @Inject constructor() : ResourcesProvider {
    override fun getClassImage(name: String): Int? {
        return when (name) {
            ARCHER_CLASS_IMAGE_KEY -> R.drawable.classe_arqueiro
            WARRIOR_CLASS_IMAGE_KEY -> R.drawable.classe_guerreiro
            MAGE_CLASS_IMAGE_KEY -> R.drawable.classe_mago
            else -> null
        }
    }

    override fun getSpecializationImage(name: String): Int? {
        return when (name) {
            GUARDIAN_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_guardiao
            GLADIATOR_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_gladiador
            WATER_MAGE_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_mago_agua
            FIRE_MAGE_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_mago_fogo
            ENGINEER_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_engenheiro
            BEASTMASTER_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_mestre_feras
            else -> null
        }
    }

    override fun getBattleClassImage(name: String): Int? {
        return when (name) {
            BATTLE_IMAGE_ARCHER_CLASS_IMAGE_KEY -> R.drawable.classe_arqueiro_16_9
            BATTLE_IMAGE_WARRIOR_CLASS_IMAGE_KEY -> R.drawable.classe_guerreiro_16_9
            BATTLE_IMAGE_MAGE_CLASS_IMAGE_KEY -> R.drawable.classe_mago_16_9
            else -> null
        }
    }

    override fun getBattleSpecializationImage(name: String): Int? {
        return when (name) {
            BATTLE_IMAGE_GUARDIAN_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_guardiao_16_9
            BATTLE_IMAGE_GLADIATOR_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_gladiador_16_9
            BATTLE_IMAGE_WATER_MAGE_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_mago_agua_16_9
            BATTLE_IMAGE_FIRE_MAGE_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_mago_fogo_16_9
            BATTLE_IMAGE_ENGINEER_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_engenheiro_16_9
            BATTLE_IMAGE_BEASTMASTER_SPECIALIZATION_IMAGE_KEY -> R.drawable.especializacao_mestre_feras_16_9
            else -> null
        }
    }

    override fun getProfileClassImage(name: String): Int? {
        return when (name) {
            ARCHER_CLASS_PROFILE_IMAGE_KEY -> R.drawable.classe_arqueiro_perfil
            WARRIOR_CLASS_PROFILE_IMAGE_KEY -> R.drawable.classe_guerreiro_perfil
            MAGE_CLASS_PROFILE_IMAGE_KEY -> R.drawable.classe_mago_perfil
            else -> null
        }
    }

    override fun getProfileSpecializationImage(name: String): Int? {
        return when (name) {
            GUARDIAN_SPECIALIZATION_PROFILE_IMAGE_KEY -> R.drawable.especializacao_guardiao_perfil
            GLADIATOR_SPECIALIZATION_PROFILE_IMAGE_KEY -> R.drawable.especializacao_gladiador_perfil
            WATER_MAGE_SPECIALIZATION_PROFILE_IMAGE_KEY -> R.drawable.especializacao_mago_agua_perfil
            FIRE_MAGE_SPECIALIZATION_PROFILE_IMAGE_KEY -> R.drawable.especializacao_mago_fogo_perfil
            ENGINEER_SPECIALIZATION_PROFILE_IMAGE_KEY -> R.drawable.especializacao_engenheiro_perfil
            BEASTMASTER_SPECIALIZATION_PROFILE_IMAGE_KEY -> R.drawable.especializacao_mestre_feras_perfil
            else -> null
        }
    }

    override fun getBattleMobImage(name: String): Int? {
        return when (name) {
            BATTLE_IMAGE_GOBLIN_HEALER_KEY -> R.drawable.goblin_curandeiro_16_9
            BATTLE_IMAGE_GOBLIN_WARRIOR_KEY -> R.drawable.goblin_guerreiro_16_9
            BATTLE_IMAGE_GOBLIN_XAMA_KEY -> R.drawable.goblin_xama_16_9
            BATTLE_IMAGE_ORC_KEY -> R.drawable.orc_das_cavernas_16_9
            else -> null
        }
    }

    override fun getSkillImage(name: String): Int? {
        return when (name) {
            SKILL_BASTION_KEY -> R.drawable.skill_bastiao
            SKILL_BLIZZARD_KEY -> R.drawable.skill_nevasca
            SKILL_WHIRLWIND_KEY -> R.drawable.skill_redemoinho
            SKILL_ARCANE_FOCUS_KEY -> R.drawable.skill_foco_arcano
            SKILL_FIREBALL_KEY -> R.drawable.skill_bola_de_fogo
            SKILL_BRUTAL_SLASH_KEY -> R.drawable.skill_corte_brutal
            SKILL_BRUTAL_RIFT_KEY -> R.drawable.skill_fenda_brutal
            SKILL_HEAVY_STRIKE_KEY -> R.drawable.skill_golpe_pesado
            SKILL_FOCUS_SKIN_KEY -> R.drawable.skill_pele_de_foco
            SKILL_TACTICAL_RETREAT_KEY -> R.drawable.skill_recuo_tatico
            SKILL_PRECISE_SHOT_KEY -> R.drawable.skill_tiro_preciso
            SKILL_TACTICAL_ADVANCE_KEY -> R.drawable.skill_avanco_tatico
            SKILL_COUNTER_ATTACK_KEY -> R.drawable.skill_contra_ataque
            SKILL_ICE_LANCE_KEY -> R.drawable.skill_lanca_de_gelo
            SKILL_PERFECT_AIM_KEY -> R.drawable.skill_mira_perfeita
            SKILL_ARCANE_MISSILE_KEY -> R.drawable.skill_missil_arcano
            SKILL_RAPID_FIRE_KEY -> R.drawable.skill_disparo_rapido
            SKILL_HOLY_SHIELD_KEY -> R.drawable.skill_escudo_sagrado
            SKILL_FIRE_BLAST_KEY -> R.drawable.skill_explosao_ignea
            SKILL_EAGLE_EYE_KEY -> R.drawable.skill_olhar_de_aguia
            SKILL_SHOCKWAVE_KEY -> R.drawable.skill_onda_de_choque
            SKILL_CANNON_SHOT_KEY -> R.drawable.skill_tiro_de_canhao
            SKILL_ESSENCE_DRAIN_KEY -> R.drawable.skill_drenar_essencia
            SKILL_BLOOD_RAGE_KEY -> R.drawable.skill_furia_sangrenta
            SKILL_BLOOD_STRIKE_KEY -> R.drawable.skill_golpe_sangrento
            SKILL_INVIGORATING_HEAL_KEY -> R.drawable.skill_cura_revigorante
            SKILL_BATTLE_RAGE_KEY -> R.drawable.skill_furia_de_batalha
            SKILL_FLAMING_TOUCH_KEY -> R.drawable.skill_toque_flamejante
            SKILL_GROUND_TRAP_KEY -> R.drawable.skill_armadilha_de_solo
            SKILL_METEOR_SHOWER_KEY -> R.drawable.skill_chuva_de_meteoros
            SKILL_SMOKESCREEN_KEY -> R.drawable.skill_cortina_de_fumaca
            SKILL_POISON_ARROW_KEY -> R.drawable.skill_flecha_envenenada
            SKILL_DEFENSIVE_STANCE_KEY -> R.drawable.skill_postura_defensiva
            SKILL_ARMOR_BREAK_KEY -> R.drawable.skill_quebrar_armaduras
            SKILL_PIERCING_SHOT_KEY -> R.drawable.skill_disparo_perfurante
            SKILL_AUTOMATIC_TURRET_KEY -> R.drawable.skill_torreta_automatica
            SKILL_FLASH_FREEZE_KEY -> R.drawable.skill_congelamento_rapido
            SKILL_PRECISION_SHOT_KEY -> R.drawable.skill_disparo_de_precisao
            SKILL_RELENTLESS_CHARGE_KEY -> R.drawable.skill_investida_implacavel
            SKILL_THORN_TRAP_KEY -> R.drawable.skill_armadilha_de_espinhos
            SKILL_FRAGMENTATION_GRENADE_KEY -> R.drawable.skill_granada_de_fragmentacao
            else -> null
        }
    }

    override fun getPhaseImage(name: String): Int? {
        return when (name) {
            PHASE_PURPLE_FLAME_ICON_KEY -> R.drawable.icone_fase_chama_roxa
            PHASE_CRACKED_SHIELD_ICON_KEY -> R.drawable.icone_fase_escudo_rachado
            PHASE_CHIPPED_SWORD_ICON_KEY -> R.drawable.icone_fase_espada_lascada
            PHASE_GIANT_CLUB_ICON_KEY -> R.drawable.icone_fase_porrete_gigante
            PHASE_HEART_WITH_AURA_ICON_KEY -> R.drawable.icone_fase_coracao_com_aura
            else -> null
        }
    }

    override fun getCoatArmsImage(name: String): Int? {
        return when (name) {
            MAGE_COAT_ARMS_IMAGE_KEY -> R.drawable.brasao_mago
            ARCHER_COAT_ARMS_IMAGE_KEY -> R.drawable.brasao_arqueiro
            GUARDIAN_COAT_ARMS_IMAGE_KEY -> R.drawable.brasao_guardiao
            GLADIATOR_COAT_ARMS_IMAGE_KEY -> R.drawable.brasao_gladiador
            WARRIOR_COAT_ARMS_IMAGE_KEY -> R.drawable.brasao_guerreiro
            ENGINEER_COAT_ARMS_IMAGE_KEY -> R.drawable.brasao_engenheiro
            WATER_MAGE_COAT_ARMS_IMAGE_KEY -> R.drawable.brasao_mago_de_agua
            FIRE_MAGE_COAT_ARMS_IMAGE_KEY -> R.drawable.brasao_mago_de_fogo
            BEASTMASTER_COAT_ARMS_IMAGE_KEY -> R.drawable.brasao_mestre_das_feras
            else -> null
        }
    }
}
