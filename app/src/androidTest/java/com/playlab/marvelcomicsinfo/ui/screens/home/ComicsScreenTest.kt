package com.playlab.marvelcomicsinfo.ui.screens.home

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.test.filters.MediumTest
import coil.annotation.ExperimentalCoilApi
import com.playlab.marvelcomicsinfo.MarvelNavHost
import com.playlab.marvelcomicsinfo.data.preferences.PreferencesDataStore
import com.playlab.marvelcomicsinfo.repository.FakeComicRepositoryAndroidTest
import com.playlab.marvelcomicsinfo.repository.FakePreferencesRepositoryAndroidTest
import com.playlab.marvelcomicsinfo.screens.PreferencesViewModel
import com.playlab.marvelcomicsinfo.screens.ScreenRoutes
import com.playlab.marvelcomicsinfo.screens.home.ComicsViewModel
import com.playlab.marvelcomicsinfo.screens.search.SearchViewModel
import com.playlab.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@OptIn(
    ExperimentalPagingApi::class,
    ExperimentalCoilApi::class
)
@MediumTest
@HiltAndroidTest
class ComicsScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Inject
    @Named("test_pds")
    lateinit var preferencesDataStore: PreferencesDataStore

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickSearch_navigateToSearchScreen(){

        val preferencesViewModel = PreferencesViewModel(FakePreferencesRepositoryAndroidTest())
        val comicsViewModel = ComicsViewModel( FakeComicRepositoryAndroidTest())
        val searchViewModel = SearchViewModel( FakeComicRepositoryAndroidTest())

        composeTestRule.setContent {
            MarvelComicsInfoTheme () {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())

                MarvelNavHost(
                    navController = navController,
                    preferencesViewModel = preferencesViewModel,
                    comicsViewModel = comicsViewModel,
                    searchViewModel = searchViewModel
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Search Icon").performClick()
        composeTestRule.onNodeWithTag(ScreenRoutes.ComicSearch.name).assertIsDisplayed()
    }

    @OptIn(ExperimentalCoilApi::class)
    @Test
    fun clickThemeIcon_switchDarkTheme(){

        val isInDarkTheme = true

        composeTestRule.setContent {
            val preferencesViewModel = PreferencesViewModel(FakePreferencesRepositoryAndroidTest())
            preferencesViewModel.switchTheme(isInDarkTheme)

            val darkThemeStoredValue = preferencesViewModel.isDarkTheme.collectAsState(null).value

            MarvelComicsInfoTheme {

                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())

                MarvelNavHost(
                    modifier = Modifier.testTag("isInDarkTheme $darkThemeStoredValue"),
                    navController = navController,
                    preferencesViewModel = preferencesViewModel,
                    comicsViewModel = null,
                    searchViewModel = null
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Theme Icon").performClick()
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("THEME_SWITCH_TEST")
        composeTestRule.onNodeWithTag("isInDarkTheme ${!isInDarkTheme}").assertExists()

    }
}