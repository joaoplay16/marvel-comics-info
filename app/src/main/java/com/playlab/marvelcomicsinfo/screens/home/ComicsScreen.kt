package com.playlab.marvelcomicsinfo.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import com.playlab.marvelcomicsinfo.R
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.screens.common.ListComic
import com.playlab.marvelcomicsinfo.screens.components.AppTopAppBar
import com.playlab.marvelcomicsinfo.screens.components.CopyrightContainer
import com.playlab.marvelcomicsinfo.ui.animations.LoadingAnimation
import com.playlab.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme

@ExperimentalPagingApi
@Composable
fun ComicsScreen(
    items: LazyPagingItems<Comic>?,
    copyright: String?,
    modifier: Modifier = Modifier,
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
    ) { paddingValues ->
        CopyrightContainer(
            copyright = copyright,
            modifier = modifier.padding(paddingValues)
        ){ modifier ->
            if (items?.itemSnapshotList?.isNotEmpty() == true) {
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

@OptIn(ExperimentalPagingApi::class)
@Preview
@Composable
fun ComicsScreenPreview(){
    MarvelComicsInfoTheme {
        Surface {
            ComicsScreen(
                items = null,
                copyright = "Marvel",
                onComicClick = {},
                onSearchClicked = {},
                onSwitchClicked = {},
            )
        }
    }
}

