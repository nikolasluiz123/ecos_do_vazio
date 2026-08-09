package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history

import androidx.room.Dao
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseEntity

@Dao
interface HistoryPhaseRoomDAO : HistoryPhaseLocalDataSource, RoomLocalDataSource<HistoryPhaseEntity>
