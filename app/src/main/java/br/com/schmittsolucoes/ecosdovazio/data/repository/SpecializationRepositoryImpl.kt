package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.specialization.SpecializationLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SpecializationRepository
import javax.inject.Inject

class SpecializationRepositoryImpl @Inject constructor(
    private val specializationLocalDataSource: SpecializationLocalDataSource,
) : SpecializationRepository {

    override suspend fun save(specializations: List<Specialization>) {
        specializationLocalDataSource.upsert(specializations.map { it.toEntity() })
    }
}
