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
import com.playlab.marvelcomicsinfo.screens.ScreenRoutes
import com.playlab.marvelcomicsinfo.screens.details.ComicDetailScreen
import com.playlab.marvelcomicsinfo.screens.home.ComicsScreen
import com.playlab.marvelcomicsinfo.screens.home.ComicsViewModel
import com.playlab.marvelcomicsinfo.screens.search.SearchScreen
import com.playlab.marvelcomicsinfo.screens.search.SearchViewModel
import com.playlab.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import com.playlab.marvelcomicsinfo.ui.theme.ThemeViewModel
import com.playlab.marvelcomicsinfo.util.Constants.COMIC_NAV_KEY
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalCoilApi
@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val themeViewModel by viewModels<ThemeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           val darkTheme by themeViewModel.isDarkTheme.collectAsState(null)
            MarvelComicsInfoTheme(
                darkTheme = darkTheme ?: isSystemInDarkTheme()
            ) {
                MarvelNavHost(themeViewModel = themeViewModel)
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
    themeViewModel: ThemeViewModel? = hiltViewModel(),
    comicsViewModel: ComicsViewModel? = hiltViewModel(),
    searchViewModel: SearchViewModel? = hiltViewModel()
) {

    val comics = comicsViewModel?.dbComics?.collectAsLazyPagingItems()
    val copyright = comicsViewModel?.copyright?.collectAsState(null)?.value ?: ""
    val isThemeStored by themeViewModel!!.isDarkTheme.collectAsState(initial = null)
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
                onSwitchClicked = { themeViewModel?.switchTheme(isThemeStored, isSystemInDarkTheme) },
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
