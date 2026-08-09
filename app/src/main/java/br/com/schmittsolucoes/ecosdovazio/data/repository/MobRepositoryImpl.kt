package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.mobs.MobLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.domain.repository.MobRepository
import javax.inject.Inject

class MobRepositoryImpl @Inject constructor(
    private val mobLocalDataSource: MobLocalDataSource,
): MobRepository {
    override suspend fun save(mobs: List<Mob>) {
        val mobEntities = mobs.map { it.toEntity() }
        mobLocalDataSource.upsert(mobEntities)
    }
}
