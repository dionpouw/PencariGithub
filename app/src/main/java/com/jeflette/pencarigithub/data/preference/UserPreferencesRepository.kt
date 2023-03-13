package com.jeflette.pencarigithub.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val themeMode = booleanPreferencesKey("theme_mode")

    fun readThemeMode(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeMode] ?: false
        }
    }

    suspend fun saveThemeMode(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeMode] = isDarkMode
        }
    }
}