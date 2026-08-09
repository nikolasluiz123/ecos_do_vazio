package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob

interface MobRepository {
    suspend fun save(mobs: List<Mob>)
    suspend fun getExistsMob(): Boolean
    suspend fun getAllMobs(): List<Mob>
}
