package com.playlab.marvelcomicsinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.screens.PreferencesViewModel
import com.playlab.marvelcomicsinfo.screens.ScreenRoutes
import com.playlab.marvelcomicsinfo.screens.details.ComicDetailScreen
import com.playlab.marvelcomicsinfo.screens.home.ComicsScreen
import com.playlab.marvelcomicsinfo.screens.home.ComicsViewModel
import com.playlab.marvelcomicsinfo.screens.search.SearchScreen
import com.playlab.marvelcomicsinfo.screens.search.SearchViewModel
import com.playlab.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import com.playlab.marvelcomicsinfo.util.Constants.COMIC_NAV_KEY
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@ExperimentalCoilApi
@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val preferencesViewModel by viewModels<PreferencesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           val isDarkThemeStoredValue by preferencesViewModel.isDarkTheme.collectAsState(null)
            MarvelComicsInfoTheme(
                darkTheme = isDarkThemeStoredValue ?: isSystemInDarkTheme()
            ) {
                MarvelNavHost(preferencesViewModel = preferencesViewModel)
            }
        }
    }
}

@OptIn(ExperimentalPagingApi::class)
@ExperimentalCoilApi
@Composable
fun MarvelNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    preferencesViewModel: PreferencesViewModel? = hiltViewModel(),
    comicsViewModel: ComicsViewModel? = hiltViewModel(),
    searchViewModel: SearchViewModel? = hiltViewModel()
) {

    val comics = comicsViewModel?.dbComics?.collectAsLazyPagingItems()
    val year = Calendar.getInstance().get(Calendar.YEAR)
    val copyright ="© Marvel $year"
    val isDarkThemePreferenceValue by preferencesViewModel!!.isDarkTheme.collectAsState(initial = null)
    val isSystemInDarkTheme = isSystemInDarkTheme()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.Comics.name,
        modifier = modifier
    ){
        composable(ScreenRoutes.Comics.name){
            ComicsScreen(
                items = comics,
                copyright = copyright,
                onSearchClicked = { navigateToScreen(navController, ScreenRoutes.ComicSearch.name)},
                onSwitchClicked = {
                    preferencesViewModel?.switchTheme(isDarkThemePreferenceValue ?: isSystemInDarkTheme)
                },
                onComicClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(COMIC_NAV_KEY, it)
                    navigateToScreen(navController, ScreenRoutes.ComicDetails.name)
                }
            )
        }
        composable(ScreenRoutes.ComicDetails.name){
            val comic =
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Comic>(COMIC_NAV_KEY)

            comic?.let {
                ComicDetailScreen(comic, copyright, navController)
            }
        }
        composable(route = ScreenRoutes.ComicSearch.name){

            val searchQuery by searchViewModel!!.searchQuery
            val comicsResults = searchViewModel!!.searchedComics.collectAsLazyPagingItems()

            SearchScreen(
                copyright = copyright,
                searchQuery = searchQuery,
                comicsResults = comicsResults,
                onTextChange = {
                    searchViewModel.updateSearchQuery(it)
                },
                onSearchClicked = {
                    searchViewModel.searchComics(it)
                },
                onCloseClicked = {
                    navController.popBackStack()
                },
                onComicClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(COMIC_NAV_KEY, it)
                    navController.navigate(ScreenRoutes.ComicDetails.name)
                }
            )
        }
    }
}
private fun navigateToScreen(
    navController: NavHostController,
    screen: String,
) {
    navController.navigate(screen)
}
