package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.skills

import androidx.room.Dao
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.SkillEntity

@Dao
interface SkillRoomDAO : SkillLocalDataSource, RoomLocalDataSource<SkillEntity>
