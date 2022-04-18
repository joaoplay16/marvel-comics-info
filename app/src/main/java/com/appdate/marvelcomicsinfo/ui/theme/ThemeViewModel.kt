package com.appdate.marvelcomicsinfo.ui.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appdate.marvelcomicsinfo.data.preferences.PreferencesDataStore
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