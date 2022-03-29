package com.appdate.marvelcomicsinfo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appdate.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import com.appdate.marvelcomicsinfo.model.Comic

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
            startDestination = MarvelScreen.Series.name,
            modifier = modifier
        ){
            composable(MarvelScreen.Series.name){ backStackEntry ->

                ComicsScreen(
                    viewModel = comicsViewModel,
                    onComicClick = {
                        val bundle = Bundle().apply { putParcelable("comic", it) }
                        navigateToSingleScreen(navController, MarvelScreen.SingleSerie.name, bundle)
                    })

            }
            composable(MarvelScreen.SingleSerie.name){ backStackEntry ->
                val comic = backStackEntry.arguments?.getParcelable<Comic>("comic")
                Log.d("COMIC", "${ comic?.title}")

                SingleComicScreen(comic)
            }
        }
    }
}


private fun navigateToSingleScreen(
    navController: NavHostController,
    screen: String,
    args: Bundle?
) {
    if (args == null){
        navController.navigate(screen)
        return
    }
    navController.navigate(MarvelScreen.SingleSerie.name, args)
}


fun NavHostController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}