package com.playlab.marvelcomicsinfo.screens.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.playlab.marvelcomicsinfo.R
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.screens.ComicCard
import com.playlab.marvelcomicsinfo.screens.Copyright
import com.playlab.marvelcomicsinfo.ui.animations.LoadingAnimation

@Composable
fun ListComic (
    items: LazyPagingItems<Comic>,
    copyright: String?,
    onComicClick: (Comic) -> Unit,
){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        if(items.itemSnapshotList.isNotEmpty()) {
            LazyColumn(Modifier.weight(1f)) {
                items(items = items, key = { it.id }) { comic ->
                    comic?.let {
                        ComicCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 6.dp),
                            title = comic.title,
                            description = comic.description,
                            imageUrl = "${comic.thumbnail.path}.${comic.thumbnail.extension}",
                            onComicClick = { onComicClick(comic) }
                        )
                    }
                }
            }
            copyright?.let { text ->
                Copyright(text = stringResource(R.string.data_provided, text))
            }
        }else{
            LoadingAnimation(circleSize = 15.dp, spaceBetween = 5.dp)
        }
    }
}