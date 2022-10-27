package com.playlab.marvelcomicsinfo.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    }

    val isDarkTheme: Flow<Boolean?> = dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_KEY]
        }

    suspend fun setDarkTheme(name: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = name
        }
    }
}