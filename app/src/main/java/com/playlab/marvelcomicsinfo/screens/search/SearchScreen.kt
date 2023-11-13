package com.playlab.marvelcomicsinfo.screens.search

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.playlab.marvelcomicsinfo.R
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.screens.ScreenRoutes
import com.playlab.marvelcomicsinfo.screens.common.ListComic
import com.playlab.marvelcomicsinfo.screens.components.CopyrightContainer
import com.playlab.marvelcomicsinfo.screens.components.SearchWidget
import com.playlab.marvelcomicsinfo.ui.animations.LoadingAnimation
import com.playlab.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import kotlinx.coroutines.flow.MutableStateFlow

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
    var loading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(
        key1 = comicsResults.itemCount,
        block = {
            Log.d(
                "PAGING",
                "ITEMS ${comicsResults.itemSnapshotList.items}"
            )
            if (comicsResults.loadState.append is LoadState.Loading)
                loading = false
        }
    )

    Scaffold(
        modifier = Modifier.testTag(ScreenRoutes.ComicSearch.name),
        topBar = {
            SearchWidget(
                text = searchQuery,
                onTextChange = onTextChange,
                onSearchClicked = {
                    if (searchQuery.isBlank()) return@SearchWidget
                    loading = true
                    onSearchClicked(it)
                },
                onCloseClicked = onCloseClicked
            )
        },
    ) { paddingValues ->
        CopyrightContainer(
            copyright = copyright,
            modifier = modifier.padding(paddingValues)
        ) { modifier ->
            if (loading) {
                when (comicsResults.loadState.refresh) {
                    is LoadState.Error -> {
                        Text(stringResource(id = R.string.comic_not_found))
                    }

                    else -> {
                        LoadingAnimation(
                            modifier = modifier,
                            circleSize = 15.dp,
                            spaceBetween = 5.dp
                        )
                    }
                }

            } else {
                ListComic(
                    modifier = modifier,
                    items = comicsResults,
                    onComicClick = onComicClick
                )
            }
        }
    }
}

@OptIn(
    ExperimentalPagingApi::class,
    ExperimentalCoilApi::class
)
@Preview
@Composable
fun SearchScreenPreview() {
    MarvelComicsInfoTheme(darkTheme = true) {
        Surface {
            val lpi: LazyPagingItems<Comic> =
                MutableStateFlow<PagingData<Comic>>(PagingData.empty())
                    .collectAsLazyPagingItems()
            SearchScreen(
                comicsResults = lpi,
                copyright = "Marvel",
                searchQuery = "",
                onComicClick = {},
                onSearchClicked = {},
                onCloseClicked = {},
                onTextChange = {},
            )
        }
    }
}