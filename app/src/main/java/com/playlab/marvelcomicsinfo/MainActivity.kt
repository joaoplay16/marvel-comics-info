package com.playlab.marvelcomicsinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.playlab.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import com.playlab.marvelcomicsinfo.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalCoilApi
@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val comicsViewModel by viewModels<ComicsViewModel>()
    private val themeViewModel by viewModels<ThemeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           val darkTheme by themeViewModel.isDarkTheme.collectAsState(null)
            MarvelComicsInfoTheme(
                darkTheme = darkTheme ?: isSystemInDarkTheme()

            ) {
                ComicsActivityScreen()
            }
        }
    }

    @ExperimentalCoilApi
    @Composable
    fun ComicsActivityScreen() {
        val navController = rememberNavController()
        MarvelNavHost(navController = navController, modifier = Modifier)
    }

    @ExperimentalCoilApi
    @Composable
    fun MarvelNavHost(
        navController: NavHostController,
        modifier: Modifier
    ) {
        val comics = comicsViewModel.dbComics.collectAsLazyPagingItems()
        val copyright by comicsViewModel.copyright.collectAsState(null)
        val isThemeStored by themeViewModel.isDarkTheme.collectAsState(null)
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
                    onSwitchClicked = { themeViewModel.switchTheme(isThemeStored, isSystemInDarkTheme) },
                    onComicClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("comic", it)
                        navigateToScreen(navController, ScreenRoutes.ComicDetails.name)
                    }
                )
            }
            composable(ScreenRoutes.ComicDetails.name){
                val comic =
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<Comic>("comic")

                comic?.let {
                    ComicDetailScreen(comic, copyright, navController)
                }
            }
            composable(route = ScreenRoutes.ComicSearch.name){
                SearchScreen(navController = navController, copyright = copyright)
            }
        }
    }
}

private fun navigateToScreen(
    navController: NavHostController,
    screen: String,
) {
    navController.navigate(screen)
}
