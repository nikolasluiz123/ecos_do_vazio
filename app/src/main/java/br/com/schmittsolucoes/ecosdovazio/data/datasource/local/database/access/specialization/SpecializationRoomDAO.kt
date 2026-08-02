package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.specialization

import androidx.room.Dao
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.SpecializationEntity

@Dao
interface SpecializationRoomDAO: SpecializationLocalDataSource, RoomLocalDataSource<SpecializationEntity> {
}