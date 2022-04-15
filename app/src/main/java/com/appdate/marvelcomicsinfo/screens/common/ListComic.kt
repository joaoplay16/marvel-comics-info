package com.appdate.marvelcomicsinfo.screens.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.appdate.marvelcomicsinfo.R
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.screens.ComicCard
import com.appdate.marvelcomicsinfo.screens.Copyright

@Composable
fun ListComic (
    items: LazyPagingItems<Comic>,
    copyright: String?,
    onComicClick: (Comic) -> Unit,
){
    Column {
        LazyColumn(Modifier.weight(1f)) {
            items(items = items, key = {it.id}) { comic ->
                comic?.let{
                    ComicCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 6.dp),
                        title = comic.title,
                        description = comic.description,
                        imageUrl = "${comic.thumbnail.path}.${comic.thumbnail.extension}",
                        onComicClick = { onComicClick(comic)  }
                    )
                }
            }
        }
        copyright?.let { text ->
            Copyright(text = stringResource(R.string.data_provided, text ))
        }
    }
}