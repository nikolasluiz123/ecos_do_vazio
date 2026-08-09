package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.mobs

import androidx.room.Dao
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.MobEntity

@Dao
interface MobRoomDAO : MobLocalDataSource, RoomLocalDataSource<MobEntity>
