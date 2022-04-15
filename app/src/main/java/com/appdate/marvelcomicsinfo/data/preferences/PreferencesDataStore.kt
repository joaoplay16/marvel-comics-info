package com.appdate.marvelcomicsinfo.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        val COPYRIGHT_KEY = stringPreferencesKey("copyright")
    }

    val getCopyright: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[COPYRIGHT_KEY]
        }

    suspend fun saveCopyright(name: String) {
        dataStore.edit { preferences ->
            preferences[COPYRIGHT_KEY] = name
        }
    }
}