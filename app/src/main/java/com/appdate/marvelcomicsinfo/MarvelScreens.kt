package com.appdate.marvelcomicsinfo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appdate.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import com.appdate.retrofit.Comic

@Composable
fun ComicsScreen(viewModel: ComicsViewModel) {
    Scaffold(
        topBar = { getTopAppBar()},
    ) {
        val comicsResponse by viewModel.comics.observeAsState()

        LazyColumn{
            comicsResponse?.let {
                val items = comicsResponse!!.data!!.results!!.filterNot {
                    it.thumbnail.path!!.matches(Regex("^.*/image_not_available\$"))
                }

                items(items = items){ comic ->
                    key(comic.id) {
                        ComicCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 6.dp),
                            title = comic.title,
                            description = comic.description,
                            imageUrl = "${comic.thumbnail.path}.${comic.thumbnail.extension}"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SingleComicScreen(comic: Comic? = null) {
    Scaffold(
        topBar = { getTopAppBar() },
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

            AsyncImage(
                model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data("http://i.annihil.us/u/prod/marvel/i/mg/9/d0/51926fde9c18a.jpg")
                    .crossfade(true)
                    .build(),
                contentDescription = "")

            Text(
                text = "Carnage",
                style = TextStyle(
                    fontSize = 50.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_condensed_regular)),
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold

                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textDecoration = TextDecoration.Underline,
            )
            ComicTextInfo("Published:", "March 16, 2022")
            Spacer(modifier = Modifier.height(2.dp))
            ComicTextInfo("Writer:", "David Michelinie, Ram. V.")
            Spacer(modifier = Modifier.height(2.dp))
            ComicTextInfo("Cover artist:", "kunkka")
            Spacer(modifier = Modifier.height(2.dp))
            ComicTextInfo("Penciler:", "Ron Lim, Francesco Manna")
            Spacer(modifier = Modifier.height(18.dp))
            /*  Spacer(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .width(100.dp)
                    .height(0.5.dp)
                    .background(MaterialTheme.colors.secondary),
            )
            Spacer(modifier = Modifier.height(18.dp))*/
        }

    }

}
@Composable
fun getTopAppBar(
    title: String? = null
) {
    return TopAppBar(
        title = { Text(text = title ?: LocalContext.current.getString(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.primary
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
        SingleComicScreen()
    }
}