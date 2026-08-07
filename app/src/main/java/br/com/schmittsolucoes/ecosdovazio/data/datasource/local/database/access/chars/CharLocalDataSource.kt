package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.CharEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHeaderTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharSelectionTuple
import kotlinx.coroutines.flow.Flow

interface CharLocalDataSource: EntityLocalDataSource<CharEntity> {
    suspend fun getExistsByName(name: String): Boolean
    fun getUserChars(userId: String): Flow<List<CharSelectionTuple>>
    fun getCharHeader(charId: String): Flow<CharHeaderTuple?>
}