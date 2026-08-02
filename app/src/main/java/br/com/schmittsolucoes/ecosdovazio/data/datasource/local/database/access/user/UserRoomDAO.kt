package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.user

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.UserEntity

@Dao
interface UserRoomDAO: UserLocalDataSource, RoomLocalDataSource<UserEntity> {

    @Query("select exists(select 1 from users)")
    override suspend fun getExistsUser(): Boolean
}