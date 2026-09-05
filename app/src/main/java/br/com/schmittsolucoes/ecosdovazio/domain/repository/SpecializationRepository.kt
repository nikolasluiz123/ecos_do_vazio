package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.SpecializationSelection
import kotlinx.coroutines.flow.Flow

interface SpecializationRepository {
    suspend fun save(specializations: List<Specialization>)
    fun getSpecializationsForSelection(charId: String, languageTag: String): Flow<List<SpecializationSelection>>
}
