package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.specialization

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.SpecializationEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.SpecializationSelectionTuple
import kotlinx.coroutines.flow.Flow

interface SpecializationLocalDataSource: EntityLocalDataSource<SpecializationEntity> {
    fun getSpecializationsForSelection(charId: String, languageTag: String): Flow<List<SpecializationSelectionTuple>>
}
