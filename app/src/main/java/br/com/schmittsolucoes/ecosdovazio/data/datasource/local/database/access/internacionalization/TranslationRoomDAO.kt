package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationEntity

@Dao
interface TranslationRoomDAO: TranslationLocalDataSource, RoomLocalDataSource<TranslationEntity> {

    @Query("select exists(select 1 from translations)")
    override suspend fun getExistsTranslation(): Boolean
}
