package com.playlab.marvelcomicsinfo.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakePreferencesRepository : PreferencesRepository {

    private var isDarkThemeStateStored: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    override fun isDarkTheme(): Flow<Boolean?> = isDarkThemeStateStored

    override suspend fun setIsDarkTheme(isDarkTheme: Boolean){
        isDarkThemeStateStored.value = isDarkTheme
    }
}