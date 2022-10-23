package com.playlab.marvelcomicsinfo.screens.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.screens.CopyrightContainer
import com.playlab.marvelcomicsinfo.screens.ScreenRoutes
import com.playlab.marvelcomicsinfo.screens.SearchWidget
import com.playlab.marvelcomicsinfo.screens.common.ListComic
import kotlin.text.Typography.copyright

@ExperimentalPagingApi
@ExperimentalCoilApi
@Composable
fun SearchScreen(
    copyright: String?,
    modifier: Modifier = Modifier,
    searchQuery: String,
    comicsResults: LazyPagingItems<Comic>,
    onComicClick: (Comic) -> Unit,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(ScreenRoutes.ComicSearch.name),
        topBar = {
            SearchWidget(
                text = searchQuery,
                onTextChange = onTextChange,
                onSearchClicked = onSearchClicked,
                onCloseClicked = onCloseClicked
            )
        },
    ){ paddingValues ->
        CopyrightContainer(
            copyright = copyright,
            modifier = modifier.padding(paddingValues)
        ) { modifier ->
            ListComic(
                items = comicsResults,
                modifier = modifier,
                onComicClick = onComicClick
            )
        }
    }
}