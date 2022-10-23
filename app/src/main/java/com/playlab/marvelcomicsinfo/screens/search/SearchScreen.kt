package com.playlab.marvelcomicsinfo.screens.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by searchViewModel.searchQuery
    val searchedComics = searchViewModel.searchedComics.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.testTag(ScreenRoutes.ComicSearch.name),
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
    ){ paddingValues ->
        CopyrightContainer(
            copyright = copyright,
            modifier = modifier.padding(paddingValues)
        ) { modifier ->
            ListComic(items = searchedComics,
                modifier = modifier,
                onComicClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("comic", it)
                    navController.navigate(ScreenRoutes.ComicDetails.name)
                })
        }
    }
}