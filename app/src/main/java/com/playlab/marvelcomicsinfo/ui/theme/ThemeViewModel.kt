package com.playlab.marvelcomicsinfo.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playlab.marvelcomicsinfo.data.preferences.PreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val dataStore: PreferencesDataStore
): ViewModel(){

    val isDarkTheme get() = dataStore.isDarkTheme

    fun switchTheme(storedThemeValue: Boolean?, isSystemInDarkTheme: Boolean){
        viewModelScope.launch {
            if (storedThemeValue == null) dataStore.setDarkTheme(!isSystemInDarkTheme)
            else dataStore.setDarkTheme(!storedThemeValue)
        }
    }
}