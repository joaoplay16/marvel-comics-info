package com.appdate.marvelcomicsinfo.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appdate.marvelcomicsinfo.R
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.screens.AppTopAppBar
import com.appdate.marvelcomicsinfo.screens.Copyright
import com.appdate.marvelcomicsinfo.screens.TextInfo
import com.appdate.marvelcomicsinfo.screens.TextLabel

@ExperimentalPagingApi
@Composable
fun ComicDetailScreen(comic: Comic, copyright: String?, navHostController: NavHostController) {
    Scaffold(
        topBar = {
            AppTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Arrow Back Icon"
                        )
                    }
                },
            )
        },
    ) {
        Column {
            LazyColumn(Modifier.weight(1f)) {
                item {
                    AsyncImage(
                        model =
                        ImageRequest.Builder(LocalContext.current)
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
                    { it.role }, { it.name }
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
            copyright?.let { text ->
                Copyright(text = stringResource(R.string.data_provided, text))
            }
        }
    }
}