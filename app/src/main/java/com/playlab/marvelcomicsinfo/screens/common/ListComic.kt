package com.playlab.marvelcomicsinfo.screens.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.screens.components.ComicCard

@Composable
fun ListComic (
    items: LazyPagingItems<Comic>,
    modifier: Modifier,
    onComicClick: (Comic) -> Unit,
) {
    LazyColumn(modifier) {
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
}