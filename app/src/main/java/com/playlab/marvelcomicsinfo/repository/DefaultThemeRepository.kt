package com.playlab.marvelcomicsinfo.repository

import com.playlab.marvelcomicsinfo.data.preferences.PreferencesDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultThemeRepository @Inject constructor(
    val preferencesDataStore: PreferencesDataStore
): ThemeRepository{

    override fun isDarkTheme(): Flow<Boolean?> = preferencesDataStore.isDarkTheme

    override suspend fun setIsDarkTheme(isDarkTheme: Boolean) {
        preferencesDataStore.setDarkTheme(isDarkTheme)
    }
}