package com.playlab.marvelcomicsinfo.screens.search

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.playlab.marvelcomicsinfo.screens.CopyrightContainer
import com.playlab.marvelcomicsinfo.screens.ScreenRoutes
import com.playlab.marvelcomicsinfo.screens.SearchWidget
import com.playlab.marvelcomicsinfo.screens.common.ListComic

@ExperimentalPagingApi
@ExperimentalCoilApi
@Composable
fun SearchScreen(
    navController: NavHostController,
    copyright: String?,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by searchViewModel.searchQuery
    val searchedComics = searchViewModel.searchedComics.collectAsLazyPagingItems()

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
    ){
        CopyrightContainer(copyright) { modifier ->
            ListComic(items = searchedComics,
                modifier = modifier,
                onComicClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("comic", it)
                    navController.navigate(ScreenRoutes.ComicDetails.name)
                })
        }
    }
}