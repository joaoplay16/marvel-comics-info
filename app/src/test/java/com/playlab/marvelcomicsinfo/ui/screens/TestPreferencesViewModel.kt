package com.playlab.marvelcomicsinfo.ui.screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.playlab.marvelcomicsinfo.MainCoroutineRule
import com.playlab.marvelcomicsinfo.repository.FakePreferencesRepository
import com.playlab.marvelcomicsinfo.screens.PreferencesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class TestPreferencesViewModel  {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: PreferencesViewModel

    @Before
    fun setup () {
        viewModel = PreferencesViewModel(FakePreferencesRepository())
    }

    @Test
    fun `switch theme when current theme is light, returns true`() =  runTest {
        val currentThemeIsDark = false
        viewModel.switchTheme(currentThemeIsDark)

        val isDarkThemePreference = viewModel.isDarkTheme.first()

        assertThat(isDarkThemePreference).isTrue()
    }

    @Test
    fun `switch theme when current theme is dark, returns false`() =  runTest {
        val currentThemeIsDark = true
        viewModel.switchTheme(currentThemeIsDark)

        val isDarkThemePreference = viewModel.isDarkTheme.first()

        assertThat(isDarkThemePreference).isFalse()
    }
}