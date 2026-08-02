package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars

import androidx.room.Dao
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.CharEntity

@Dao
interface CharRoomDAO: CharLocalDataSource, RoomLocalDataSource<CharEntity> {
}