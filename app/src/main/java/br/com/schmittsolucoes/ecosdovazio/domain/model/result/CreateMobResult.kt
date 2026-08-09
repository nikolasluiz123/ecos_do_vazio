package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill

data class CreateMobResult(
    val mob: Mob,
    val skills: List<Skill>
)