package com.appdate.marvelcomicsinfo.ui.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appdate.marvelcomicsinfo.data.preferences.PreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val dataStore: PreferencesDataStore
    ): ViewModel(){

    val isDarkTheme : Flow<Boolean?> get() = dataStore.isDarkTheme
    private var _isDarkTheme: MutableState<Boolean> =
        mutableStateOf(false)


    fun switchTheme(isDark: Boolean?){
        viewModelScope.launch {

               if (isDark == null) {
                   _isDarkTheme.value = false
                   dataStore.setDarkTheme(false)
               } else {
                   _isDarkTheme.value = !isDark
                   dataStore.setDarkTheme(!isDark)
               }

        }
    }
}