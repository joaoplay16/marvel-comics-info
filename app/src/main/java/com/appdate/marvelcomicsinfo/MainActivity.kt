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
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appdate.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import com.appdate.marvelcomicsinfo.model.Comic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var myContext: Context
    val comicsViewModel by viewModels<ComicsViewModel>()
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
        NavHost(
            navController = navController,
            startDestination = AppScreen.Series.name,
            modifier = modifier
        ){
            composable(AppScreen.Series.name){

                ComicsScreen(
                    viewModel = comicsViewModel,
                    onComicClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("comicAndCopyright", it)
                        navigateToSingleScreen(navController, AppScreen.SerieDetails.name)
                    })

            }
            composable(AppScreen.SerieDetails.name){
                val comicAndCopyright =
                        navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<Pair<Comic, String>>("comicAndCopyright")

                comicAndCopyright?.let {
                    SingleComicScreen(comicAndCopyright, navController)
                }
            }
        }
    }
}


private fun navigateToSingleScreen(
    navController: NavHostController,
    screen: String,
) {
    navController.navigate(screen)
}
