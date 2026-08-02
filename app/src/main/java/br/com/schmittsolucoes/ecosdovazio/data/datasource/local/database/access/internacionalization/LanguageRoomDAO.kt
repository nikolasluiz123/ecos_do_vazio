package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.LanguageEntity

@Dao
interface LanguageRoomDAO: LanguageLocalDataSource, RoomLocalDataSource<LanguageEntity> {

    @Query("select exists(select 1 from languages)")
    override suspend fun getExistsLanguage(): Boolean
}
