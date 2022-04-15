package com.appdate.marvelcomicsinfo.screens.search

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.appdate.marvelcomicsinfo.screens.ScreenRoutes
import com.appdate.marvelcomicsinfo.screens.SearchWidget
import com.appdate.marvelcomicsinfo.screens.common.ListComic

@ExperimentalPagingApi
@ExperimentalCoilApi
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by searchViewModel.searchQuery
    val searchedComics = searchViewModel.searchedImages.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchWidget(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(query = it)
                },
                onSearchClicked = {
                    searchViewModel.searchComics(query = it)
                },
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        },
        content = {
            ListComic(items = searchedComics,
                copyright = "",
                onComicClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("comic", it)
                    navController.navigate(ScreenRoutes.ComicDetails.name)
                })
        }
    )
}