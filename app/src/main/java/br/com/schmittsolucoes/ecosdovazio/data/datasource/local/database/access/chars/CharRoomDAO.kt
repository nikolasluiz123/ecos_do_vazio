package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.CharEntity

@Dao
interface CharRoomDAO: CharLocalDataSource, RoomLocalDataSource<CharEntity> {

    @Query("select exists(select * from chars where name = :name)")
    override suspend fun getExistsByName(name: String): Boolean
}