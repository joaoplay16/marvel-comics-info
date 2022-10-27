package com.playlab.marvelcomicsinfo.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playlab.marvelcomicsinfo.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
): ViewModel(){

    val isDarkTheme = preferencesRepository.isDarkTheme()

    fun switchTheme(isDarkTheme: Boolean){
        viewModelScope.launch {
            preferencesRepository.setIsDarkTheme(!isDarkTheme)
        }
    }
}