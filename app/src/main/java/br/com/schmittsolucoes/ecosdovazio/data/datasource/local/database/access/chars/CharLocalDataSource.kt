package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.CharEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.BattleCharTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharAttributesTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharBaseDamageDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharCriticalDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharDodgeDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHeaderTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHealthDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharLevelInfoTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharMagicResistanceDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharPhysicalResistanceDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharSelectionTuple
import kotlinx.coroutines.flow.Flow

interface CharLocalDataSource: EntityLocalDataSource<CharEntity> {
    suspend fun getExistsByName(name: String): Boolean
    fun getUserChars(userId: String): Flow<List<CharSelectionTuple>>
    fun getCharHeader(charId: String): Flow<CharHeaderTuple?>
    fun getCharHealthDataTuple(charId: String): Flow<CharHealthDataTuple>
    fun getCharBaseDamageDataTuple(charId: String): Flow<CharBaseDamageDataTuple>
    fun getCharPhysicalResistanceDataTuple(charId: String): Flow<CharPhysicalResistanceDataTuple>
    fun getCharMagicResistanceDataTuple(charId: String): Flow<CharMagicResistanceDataTuple>
    fun getCharCriticalDataTuple(charId: String): Flow<CharCriticalDataTuple>
    fun getCharDodgeDataTuple(charId: String): Flow<CharDodgeDataTuple>
    fun getCharAttributesDataTuple(charId: String): Flow<CharAttributesTuple>
    fun getCharLevelInfoDataTuple(charId: String): Flow<CharLevelInfoTuple>
    suspend fun getById(id: String): CharEntity
    fun getBattleChar(charId: String): Flow<BattleCharTuple>
}