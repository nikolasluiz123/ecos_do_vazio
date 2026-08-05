package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars.CharLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.Char
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
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
}