package com.playlab.marvelcomicsinfo.repository

import kotlinx.coroutines.flow.Flow

interface ThemeRepository{
    
    fun isDarkTheme(): Flow<Boolean?>

    suspend fun setIsDarkTheme(isDarkTheme: Boolean)
}