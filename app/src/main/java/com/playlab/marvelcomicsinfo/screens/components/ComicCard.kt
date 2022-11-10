package com.playlab.marvelcomicsinfo.screens.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.playlab.marvelcomicsinfo.R
import com.playlab.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme

@Composable
fun ComicCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 3.dp,
    imageUrl: String = "",
    title: String = "",
    description: String? = null,
    onComicClick: () -> Unit = {},
) {

    Card(
        elevation = elevation,
        backgroundColor = MaterialTheme.colors.surface,
        modifier = modifier,
    ) {
        val expanded = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.clickable { expanded.value = !expanded.value },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val context = LocalContext.current

            val imageRequest = remember {
                ImageRequest.Builder(context)
                    .data(imageUrl)
                    .error(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
                    .crossfade(300)
                    .build()
            }

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onComicClick() },
                model = imageRequest,
                contentDescription = title,
                contentScale = ContentScale.Crop,
            )

            CardTitle(
                text = title
            )

            if (!description.isNullOrEmpty()) {
                Box(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = description,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = if (!expanded.value) 1 else Int.MAX_VALUE,
                        overflow = if (!expanded.value) TextOverflow.Ellipsis else TextOverflow.Visible
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComicCardPreview(modifier: Modifier = Modifier) {
    MarvelComicsInfoTheme {
        ComicCard(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            elevation = 4.dp,
            imageUrl = "https://i.annihil.us/u/prod/marvel/i/mg/9/50/623b2d85dc8a4/clean.jpg",
            title = "Anim id est laborum",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do" +
                    " eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad " +
                    "minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex " +
                    "ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit" +
                    " esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                    " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        )
    }
}