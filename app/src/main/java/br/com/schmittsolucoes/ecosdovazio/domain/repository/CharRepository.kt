package br.com.schmittsolucoes.ecosdovazio.domain.repository

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
import kotlinx.coroutines.flow.Flow

interface CharRepository {
    suspend fun insert(char: Char)
    suspend fun getExistsByName(name: String): Boolean
    fun getUserChars(userId: String): Flow<List<CharSelection>>
    fun getCharHeader(charId: String): Flow<CharHeader?>
    fun getCharHealthData(charId: String): Flow<CharHealthData>
    fun getCharBaseDamageData(charId: String): Flow<CharBaseDamageData>
    fun getCharPhysicalResistanceData(charId: String): Flow<CharPhysicalResistanceData>
    fun getCharMagicResistanceData(charId: String): Flow<CharMagicResistanceData>
    fun getCharCriticalData(charId: String): Flow<CharCriticalData>
    fun getCharDodgeData(charId: String): Flow<CharDodgeData>
    fun getCharLevelInfoData(charId: String, nextLevelExperience: Long): Flow<CharLevelInfo>
    fun getCharAttributesData(charId: String, maxAttributeValue: Long): Flow<CharAttributes>
    suspend fun getById(id: String): Char
    suspend fun update(char: Char)
}