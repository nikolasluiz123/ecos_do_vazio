package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars.CharLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toDomain
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.Char
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttributes
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharBaseDamageData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharCriticalData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharDodgeData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharHeader
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharHealthData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharLevelInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharMagicResistanceData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharPhysicalResistanceData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharRepositoryImpl @Inject constructor(
    private val charLocalDataSource: CharLocalDataSource
): CharRepository {
    override suspend fun insert(char: Char) {
        charLocalDataSource.insert(listOf(char.toEntity()))
    }

    override suspend fun getExistsByName(name: String): Boolean {
        return charLocalDataSource.getExistsByName(name)
    }

    override fun getUserChars(userId: String): Flow<List<CharSelection>> {
        return charLocalDataSource.getUserChars(userId).map { tuples ->
            tuples.map { it.toDomain() }
        }
    }

    override fun getCharHeader(charId: String): Flow<CharHeader?> {
        return charLocalDataSource.getCharHeader(charId).map { it?.toDomain() }
    }

    override fun getCharHealthData(charId: String): Flow<CharHealthData> {
        return charLocalDataSource.getCharHealthDataTuple(charId).map { it.toDomain() }
    }

    override fun getCharBaseDamageData(charId: String): Flow<CharBaseDamageData> {
        return charLocalDataSource.getCharBaseDamageDataTuple(charId).map { it.toDomain() }
    }

    override fun getCharPhysicalResistanceData(charId: String): Flow<CharPhysicalResistanceData> {
        return charLocalDataSource.getCharPhysicalResistanceDataTuple(charId).map { it.toDomain() }
    }

    override fun getCharMagicResistanceData(charId: String): Flow<CharMagicResistanceData> {
        return charLocalDataSource.getCharMagicResistanceDataTuple(charId).map { it.toDomain() }
    }

    override fun getCharCriticalData(charId: String): Flow<CharCriticalData> {
        return charLocalDataSource.getCharCriticalDataTuple(charId).map { it.toDomain() }
    }

    override fun getCharDodgeData(charId: String): Flow<CharDodgeData> {
        return charLocalDataSource.getCharDodgeDataTuple(charId).map { it.toDomain() }
    }

    override fun getCharLevelInfoData(charId: String, nextLevelExperience: Long): Flow<CharLevelInfo> {
        return charLocalDataSource.getCharLevelInfoDataTuple(charId).map { it.toDomain(nextLevelExperience) }
    }

    override fun getCharAttributesData(charId: String, maxAttributeValue: Long): Flow<CharAttributes> {
        return charLocalDataSource.getCharAttributesDataTuple(charId).map { it.toDomain(maxAttributeValue) }
    }

    override fun getBattleChar(charId: String): Flow<BattleChar> {
        return charLocalDataSource.getBattleChar(charId).map { it.toDomain() }
    }

    override suspend fun getById(id: String): Char {
        return charLocalDataSource.getById(id).toDomain()
    }

    override fun getByIdObservable(id: String): Flow<Char> {
        return charLocalDataSource.getByIdObservable(id).filterNotNull().map { it.toDomain() }
    }

    override suspend fun update(char: Char) {
        charLocalDataSource.update(listOf(char.toEntity()))
    }
}