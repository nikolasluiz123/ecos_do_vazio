package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.preferences.PreferencesLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.domain.model.UserPreferences
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DataStorePreferencesRepository @Inject constructor(
    private val localDataSource: PreferencesLocalDataSource
) : PreferencesRepository {

    override fun getUserPreferences(userId: String): Flow<UserPreferences?> {
        return localDataSource.getUserPreferences(userId)
    }

    override suspend fun saveCharSelection(userId: String, charId: String) {
        val currentPrefs = localDataSource.getUserPreferences(userId).firstOrNull() ?: UserPreferences()
        localDataSource.setUserPreferences(userId, currentPrefs.copy(selectedCharId = charId))
    }

    override suspend fun clearCharSelection(userId: String) {
        val currentPrefs = localDataSource.getUserPreferences(userId).firstOrNull() ?: UserPreferences()
        localDataSource.setUserPreferences(userId, currentPrefs.copy(selectedCharId = null))
    }
}
