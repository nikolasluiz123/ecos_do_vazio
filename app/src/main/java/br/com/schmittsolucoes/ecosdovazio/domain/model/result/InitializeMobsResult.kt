package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobType

data class InitializeMobsResult(
    val mobs: List<InitializedMob>,
){
    data class InitializedMob(
        val id: String,
        val type: MobType
    )
}