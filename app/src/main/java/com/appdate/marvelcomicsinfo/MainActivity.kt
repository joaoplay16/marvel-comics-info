package com.appdate.marvelcomicsinfo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.appdate.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.screens.home.ComicsViewModel
import com.appdate.marvelcomicsinfo.screens.ScreenRoutes
import com.appdate.marvelcomicsinfo.screens.home.ComicsScreen
import com.appdate.marvelcomicsinfo.screens.details.ComicDetailScreen
import com.appdate.marvelcomicsinfo.screens.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var myContext: Context
    private val comicsViewModel by viewModels<ComicsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myContext = this
        setContent {
            MarvelComicsInfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.surface
                ) {
                    ComicsActivityScreen()
                }
            }
        }
    }

    @Composable
    fun ComicsActivityScreen() {
        val navController = rememberNavController()
        MarvelNavHost(navController = navController, modifier = Modifier)
    }

    @Composable
    fun MarvelNavHost(
        navController: NavHostController,
        modifier: Modifier
    ) {
        val comics = comicsViewModel.dbComics.collectAsLazyPagingItems()
        val copyright by comicsViewModel.copyright.collectAsState(null)
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
                SearchScreen(navController = navController)
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
