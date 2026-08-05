package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.CharEntity

interface CharLocalDataSource: EntityLocalDataSource<CharEntity> {
    suspend fun getExistsByName(name: String): Boolean
}