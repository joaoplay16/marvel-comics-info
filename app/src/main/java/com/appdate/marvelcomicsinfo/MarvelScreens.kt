package com.appdate.marvelcomicsinfo

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appdate.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.ui.animations.LoadingAnimation

@Composable
fun ComicsScreen(
    viewModel: ComicsViewModel,
    onComicClick: (Pair<Comic, String>) -> Unit) {
    Scaffold(
        topBar = { TopAppBar() },
    ) {
        val comicsResponse by viewModel.comics.observeAsState()

        if(comicsResponse != null) {
            Column {
                LazyColumn(Modifier.weight(1f)) {
                    val items = comicsResponse!!.data!!.results!!.filterNot {
                        it.thumbnail.path!!.matches(Regex("^.*/image_not_available\$"))
                    }

                    items(items = items) { comic ->
                        ComicCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 6.dp),
                            title = comic.title,
                            description = comic.description,
                            imageUrl = "${comic.thumbnail.path}.${comic.thumbnail.extension}",
                            onComicClick = { onComicClick(Pair(comic, comicsResponse!!.copyright)) }
                        )
                    }
                }
                Copyright(text = comicsResponse!!.copyright)
            }
        }else{
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                LoadingAnimation(circleSize = 15.dp, spaceBetween = 5.dp)
            }
        }
    }
}

@Composable
fun SingleComicScreen(comicAndCopyright: Pair<Comic, String>, navHostController: NavHostController ) {
    val (comic, copyright) = comicAndCopyright
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { IconButton(onClick = { navHostController.popBackStack() } ) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Arrow Back Icon")
                }})
        },
    ) {
        Column {
            LazyColumn (Modifier.weight(1f)){
                item {
                    AsyncImage(
                        model =
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data("${comic.thumbnail.path}.${comic.thumbnail.extension}")
                            .crossfade(true)
                            .build(),
                        contentDescription = comic.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop

                    )
                    Text(
                        text = comic.title,
                        style = TextStyle(
                            fontSize = 35.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                            fontWeight = FontWeight.Bold

                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                    )
                }

                comic.description?.let {
                    item {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = comic.description,
                            color = MaterialTheme.colors.onSurface,
                        )

                    }
                }

                val creatorsByRole = comic.creators?.creatorsItems?.groupBy(
                    { it.role }, {it.name}
                )

                creatorsByRole?.let { 
                    items(items = it.keys.toList().sorted()) { role ->
                        TextLabel(text = role.capitalize(Locale("en-US")))
                        creatorsByRole[role]!!.sorted().forEach { name ->
                            TextInfo(info = name)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
            }

            Copyright(text = copyright)
        }
    }
}
@Composable
fun TopAppBar(
    title: String? = null,
    navigationIcon: @Composable (() -> Unit)? = null
) {
    return TopAppBar(
        title = { Text(text = title ?: stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = navigationIcon
    )

}

@Preview(showBackground = true)
@Composable
fun ComicsPreview() {
    MarvelComicsInfoTheme {
//        ComicsScreen(comicsViewModel.comics)
    }
}

@Preview(showBackground = true)
@Composable
fun SingleComicsScreenPreview() {
    MarvelComicsInfoTheme {
//        SingleComicScreen()
    }
}