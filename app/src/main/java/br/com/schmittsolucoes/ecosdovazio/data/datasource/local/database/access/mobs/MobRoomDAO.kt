package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.mobs

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.MobEntity

@Dao
interface MobRoomDAO : MobLocalDataSource, RoomLocalDataSource<MobEntity> {
    @Query("select exists(select 1 from mobs limit 1)")
    override suspend fun getExistsMob(): Boolean

    @Query("select * from mobs")
    override suspend fun getAllMobs(): List<MobEntity>

    @Query("select * from mobs where id = :id")
    override suspend fun getById(id: String): MobEntity?
}
