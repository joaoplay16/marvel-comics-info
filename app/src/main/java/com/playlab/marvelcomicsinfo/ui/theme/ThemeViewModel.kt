package com.playlab.marvelcomicsinfo.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playlab.marvelcomicsinfo.repository.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
): ViewModel(){

    val isDarkTheme = themeRepository.isDarkTheme()

    fun switchTheme(isDarkTheme: Boolean){
        viewModelScope.launch {
            themeRepository.setIsDarkTheme(!isDarkTheme)
        }
    }
}