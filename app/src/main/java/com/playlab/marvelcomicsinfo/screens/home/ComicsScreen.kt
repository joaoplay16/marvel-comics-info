package com.playlab.marvelcomicsinfo.screens.home

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import com.playlab.marvelcomicsinfo.R
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.screens.AppTopAppBar
import com.playlab.marvelcomicsinfo.screens.CopyrightContainer
import com.playlab.marvelcomicsinfo.screens.common.ListComic
import com.playlab.marvelcomicsinfo.ui.animations.LoadingAnimation

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
        CopyrightContainer(copyright){ modifier ->
            if (items.itemSnapshotList.isNotEmpty()) {
                ListComic(
                    modifier = modifier,
                    items = items,
                    onComicClick = onComicClick,
                )
            }else{
                LoadingAnimation(circleSize = 15.dp, spaceBetween = 5.dp)
            }
        }
    }
}


