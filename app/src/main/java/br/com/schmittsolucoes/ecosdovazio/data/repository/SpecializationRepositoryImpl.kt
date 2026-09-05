package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.specialization.SpecializationLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toDomain
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.SpecializationSelection
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SpecializationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpecializationRepositoryImpl @Inject constructor(
    private val specializationLocalDataSource: SpecializationLocalDataSource,
) : SpecializationRepository {

    override suspend fun save(specializations: List<Specialization>) {
        specializationLocalDataSource.upsert(specializations.map { it.toEntity() })
    }

    override fun getSpecializationsForSelection(charId: String, languageTag: String): Flow<List<SpecializationSelection>> {
        return specializationLocalDataSource.getSpecializationsForSelection(charId, languageTag).map { list ->
            list.map { tuple -> tuple.toDomain() }
        }
    }
}
