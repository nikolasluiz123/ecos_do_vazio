package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.CharEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHeaderTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharSelectionTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface CharRoomDAO: CharLocalDataSource, RoomLocalDataSource<CharEntity> {

    @Query("select exists(select * from chars where name = :name)")
    override suspend fun getExistsByName(name: String): Boolean

    @Query("""
        select chars.id as id, 
               chars.name as name, 
               classes.presentation_image_name as presentationImageName 
         from chars
         inner join classes on classes.id = chars.class_id
         where chars.user_id = :userId
    """)
    override fun getUserChars(userId: String): Flow<List<CharSelectionTuple>>

    @Query("""
        select chars.name as name, 
               classes.profile_image_name as profileImageName
        from chars
        inner join classes on classes.id = chars.class_id
        where chars.id = :charId
    """)
    override fun getCharHeader(charId: String): Flow<CharHeaderTuple?>
}