package com.appdate.marvelcomicsinfo.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.appdate.marvelcomicsinfo.R
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.screens.AppTopAppBar
import com.appdate.marvelcomicsinfo.screens.ComicCard
import com.appdate.marvelcomicsinfo.screens.Copyright
import com.appdate.marvelcomicsinfo.screens.common.ListComic

@ExperimentalPagingApi
@Composable
fun ComicsScreen(
    items: LazyPagingItems<Comic>,
    copyright: String?,
    onComicClick: (Comic) -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onSwitchClicked: () -> Unit = {}
) {
    Scaffold(
        topBar = { AppTopAppBar(
            actions = {
                IconButton(onClick = onSwitchClicked) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Theme Icon"
                    )
                }
                IconButton(onClick = onSearchClicked) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
            }
        )
        },
    ) {
        ListComic(
            items = items,
            copyright = copyright,
            onComicClick = onComicClick,
        )
    }
}


