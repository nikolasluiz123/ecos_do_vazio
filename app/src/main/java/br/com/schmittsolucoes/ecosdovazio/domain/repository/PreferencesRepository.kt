package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getUserPreferences(userId: String): Flow<UserPreferences?>
    suspend fun saveCharSelection(userId: String, charId: String)
    suspend fun clearCharSelection(userId: String)
}
