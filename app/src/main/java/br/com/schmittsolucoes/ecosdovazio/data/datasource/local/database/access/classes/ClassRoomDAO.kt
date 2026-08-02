package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.classes

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.ClassEntity

@Dao
interface ClassRoomDAO: ClassLocalDataSource, RoomLocalDataSource<ClassEntity> {

    @Query("select exists(select 1 from classes)")
    override suspend fun getExistsClass(): Boolean
}