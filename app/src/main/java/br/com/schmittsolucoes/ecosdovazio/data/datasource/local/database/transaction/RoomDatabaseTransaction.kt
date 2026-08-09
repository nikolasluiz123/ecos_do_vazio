package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.transaction

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import br.com.schmittsolucoes.ecosdovazio.core.database.transaction.DatabaseTransaction

class RoomDatabaseTransaction(private val db: RoomDatabase): DatabaseTransaction {
    override suspend fun <T> run(block: suspend () -> T): T {
        return db.withTransaction(block)
    }

    override fun isInTransaction(): Boolean {
        return db.inTransaction()
    }
}