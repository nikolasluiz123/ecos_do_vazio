package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.preferences

import br.com.schmittsolucoes.ecosdovazio.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesLocalDataSource {
    fun getUserPreferences(userId: String): Flow<UserPreferences?>
    suspend fun setUserPreferences(userId: String, preferences: UserPreferences)
    suspend fun clearUserPreferences(userId: String)
}
