package br.com.schmittsolucoes.ecosdovazio.presentation.history.info.composables

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model.HistoryPhaseMobInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model.MobPhaseInfoUIModel

object HistoryMobsInfoPreviewData {

    fun goblinWarrior(mobCount: String = "1x") = HistoryPhaseMobInfoUIModel(
        mobPhaseInfo = MobPhaseInfoUIModel(
            mobId = "goblin_warrior",
            mobName = "Goblin Guerreiro",
            mobDescription = "Um combatente focado em ataques corpo a corpo e dano físico, atuando de forma similar à classe Guerreiro. Devido à sua biologia goblin, esta criatura possui baixa defesa e vitalidade, tornando-se mais frágil que guerreiros tradicionais.",
            mobProfileImage = R.drawable.goblin_guerreiro_perfil,
            mobCount = mobCount,
        ),
        skills = listOf(
            MobSkillUIModel.CommonDamage(
                id = "gw_1",
                name = "Ataque Rápido",
                description = "Um ataque físico ofensivo veloz que causa 15 (15 + 0 - 0) de dano e pode ser usado novamente após 1 turnos.",
                image = R.drawable.skill_ataque_rapido,
                refreshTime = 1,
                currentRefreshTime = 0,
                blocked = false,
                minLevel = 1L,
                damage = 15L,
            ),
            MobSkillUIModel.CommonDamage(
                id = "gw_2",
                name = "Corte Brutal",
                description = "Um poderoso e violento golpe ofensivo com a arma causando 20 (20 + 0 - 0) de dano. Pode ser usado novamente após 3 turnos.",
                image = R.drawable.skill_corte_brutal,
                refreshTime = 3,
                currentRefreshTime = 0,
                blocked = false,
                minLevel = 3L,
                damage = 20L,
            ),
            MobSkillUIModel.Buff(
                id = "gw_3",
                name = "Instinto Selvagem",
                description = "Aumenta instintos ofensivos em 20% por 4 turnos. Pode ser usado novamente após 4 turnos.",
                image = R.drawable.skill_instinto_selvagem,
                skillCategory = SkillCategory.OFFENSIVE_BUFF,
                refreshTime = 4,
                currentRefreshTime = 0,
                blocked = false,
                minLevel = 6L,
                multiplier = 0.2,
                duration = 4,
            ),
            MobSkillUIModel.CommonDamage(
                id = "gw_4",
                name = "Investida",
                description = "Golpe ofensivo frontal descuidado e violento que causa 25 (25 + 0 - 0) de dano. Pode ser usado novamente após 4 turnos.",
                image = R.drawable.skill_investida,
                refreshTime = 4,
                currentRefreshTime = 0,
                blocked = false,
                minLevel = 10L,
                damage = 25L,
            ),
        ),
    )

    fun goblinShaman(mobCount: String = "1x") = HistoryPhaseMobInfoUIModel(
        mobPhaseInfo = MobPhaseInfoUIModel(
            mobId = "goblin_shaman",
            mobName = "Goblin Xamã",
            mobDescription = "Um monstro de estilo mago especializado em causar dano mágico e aplicar efeitos negativos (debuffs) nos adversários. Por ser uma variação mágica da espécie goblin, possui índices de defesa e vitalidade ainda menores do que a versão guerreira.",
            mobProfileImage = R.drawable.goblin_xama_perfil,
            mobCount = mobCount,
        ),
        skills = listOf(
            MobSkillUIModel.CommonDamage(
                id = "gs_1",
                name = "Projétil Místico",
                description = "Arremessa energia mágica caótica causando 15 (15 + 0 - 0) de dano. Pode ser usado novamente após 1 turnos.",
                image = R.drawable.skill_projetil_mistico,
                refreshTime = 1,
                currentRefreshTime = 0,
                blocked = false,
                minLevel = 1L,
                damage = 15L,
            ),
            MobSkillUIModel.DamageOverTime(
                id = "gs_2",
                name = "Maldição",
                description = "Magia sombria que drena 5 (5 + 0 - 0) de saúde por 3 turnos. Pode ser usado novamente após 4 turnos.",
                image = R.drawable.skill_maldicao,
                refreshTime = 4,
                currentRefreshTime = 0,
                blocked = false,
                minLevel = 3L,
                damage = 5L,
                duration = 3,
            ),
            MobSkillUIModel.Buff(
                id = "gs_3",
                name = "Escudo de Energia",
                description = "Habilidade defensiva que protege contra impactos aumentando a resistência em 10% por 4 turnos. Pode ser usado novamente após 6 turnos.",
                image = R.drawable.skill_escudo_de_energia,
                skillCategory = SkillCategory.DEFENSIVE_BUFF,
                refreshTime = 6,
                currentRefreshTime = 0,
                blocked = false,
                minLevel = 6L,
                multiplier = 0.1,
                duration = 4,
            ),
            MobSkillUIModel.VampiricDamage(
                id = "gs_4",
                name = "Dreno de Mana",
                description = "Ataque mágico que visa drenar a energia vital do alvo causando 20 (20 + 0 - 0) de dano e restaurando 30% da vida do conjurador. Pode ser usado novamente após 4 turnos.",
                image = R.drawable.skill_dreno_de_mana,
                refreshTime = 4,
                currentRefreshTime = 0,
                blocked = false,
                minLevel = 10L,
                damage = 20L,
                multiplier = 0.3,
            ),
            MobSkillUIModel.CommonDamage(
                id = "gs_5",
                name = "Explosão Arcana",
                description = "Poderoso ataque ofensivo arcano que causa 30 (30 + 0 - 0) de dano. Pode ser usado novamente após 5 turnos.",
                image = R.drawable.skill_explosao_arcana,
                refreshTime = 5,
                currentRefreshTime = 0,
                blocked = false,
                minLevel = 15L,
                damage = 30L,
            ),
        ),
    )

    fun goblinHealer(mobCount: String = "1x") = HistoryPhaseMobInfoUIModel(
        mobPhaseInfo = MobPhaseInfoUIModel(
            mobId = "goblin_healer",
            mobName = "Goblin Curandeiro",
            mobDescription = "Uma variação de mago com foco exclusivo em suporte, utilizando magias para curar e aplicar efeitos positivos (buffs) aos seus aliados. Ele aparece em fases mais avançadas da história, atuando em conjunto com outros monstros, e compartilha a mesma extrema fragilidade física e mágica do Xamã.",
            mobProfileImage = R.drawable.goblin_curandeiro_perfil,
            mobCount = mobCount,
        ),
        skills = listOf(
            MobSkillUIModel.Heal(
                id = "gh_1",
                name = "Toque de Cura",
                description = "Uma bênção simples que restaura 20 de vida. Pode ser usado novamente após 3 turnos.",
                image = R.drawable.skill_toque_de_cura,
                skillCategory = SkillCategory.HEAL,
                refreshTime = 3,
                currentRefreshTime = 0,
                blocked = false,
                minLevel = 1L,
                lifeRestore = 20L,
            ),
            MobSkillUIModel.Heal(
                id = "gh_2",
                name = "Regeneração",
                description = "Proporciona cura para todos os aliados restaurando 15 de vida. Pode ser usado novamente após 6 turnos.",
                image = R.drawable.skill_regeneracao,
                skillCategory = SkillCategory.AREA_HEAL,
                refreshTime = 6,
                currentRefreshTime = 0,
                blocked = false,
                minLevel = 6L,
                lifeRestore = 15L,
            ),
        ),
    )

    val fewMobsPhaseInfo = listOf(
        goblinWarrior(mobCount = "1x"),
    )

    val manyMobsPhaseInfo = listOf(
        goblinWarrior(mobCount = "2x"),
        goblinShaman(mobCount = "1x"),
        goblinHealer(mobCount = "1x"),
    )

}
