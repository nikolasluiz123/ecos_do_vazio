package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars.CharLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toDomain
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.Char
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharRepositoryImpl @Inject constructor(
    private val charLocalDataSource: CharLocalDataSource
): CharRepository {
    override suspend fun insert(char: Char) {
        charLocalDataSource.insert(listOf(char.toEntity()))
    }

    override suspend fun getExistsByName(name: String): Boolean {
        return charLocalDataSource.getExistsByName(name)
    }

    override fun getUserChars(userId: String): Flow<List<CharSelection>> {
        return charLocalDataSource.getUserChars(userId).map { tuples ->
            tuples.map { it.toDomain() }
        }
    }
}