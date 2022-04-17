package com.appdate.marvelcomicsinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.screens.ScreenRoutes
import com.appdate.marvelcomicsinfo.screens.details.ComicDetailScreen
import com.appdate.marvelcomicsinfo.screens.home.ComicsScreen
import com.appdate.marvelcomicsinfo.screens.home.ComicsViewModel
import com.appdate.marvelcomicsinfo.screens.search.SearchScreen
import com.appdate.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import com.appdate.marvelcomicsinfo.ui.theme.ThemeViewModel
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
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState(initial = null)
            MarvelComicsInfoTheme(darkTheme = isDarkTheme ?: isSystemInDarkTheme()) {
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
        val isDarkTheme by themeViewModel.isDarkTheme.collectAsState(initial = null)

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
                    onSwitchClicked = { themeViewModel.switchTheme(isDarkTheme) },
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
