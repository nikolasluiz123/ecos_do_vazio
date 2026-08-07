package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.schmittsolucoes.ecosdovazio.data.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DataStorePreferencesLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesLocalDataSource {

    override fun getUserPreferences(userId: String): Flow<UserPreferences?> {
        val key = stringPreferencesKey(userId)

        return dataStore.data.map { preferences ->
            preferences[key]?.let { json ->
                Json.decodeFromString<UserPreferences>(json)
            }
        }
    }

    override suspend fun setUserPreferences(userId: String, preferences: UserPreferences) {
        val key = stringPreferencesKey(userId)
        val json = Json.encodeToString(preferences)

        dataStore.edit { settings ->
            settings[key] = json
        }
    }

    override suspend fun clearUserPreferences(userId: String) {
        val key = stringPreferencesKey(userId)

        dataStore.edit { settings ->
            settings.remove(key)
        }
    }
}
