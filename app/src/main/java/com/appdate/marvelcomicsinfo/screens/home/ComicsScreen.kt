package com.appdate.marvelcomicsinfo.screens.home

import android.graphics.drawable.VectorDrawable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import com.appdate.marvelcomicsinfo.R
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.screens.AppTopAppBar
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
                        imageVector = ImageVector.vectorResource(id = R.drawable.theme_light_dark),
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


