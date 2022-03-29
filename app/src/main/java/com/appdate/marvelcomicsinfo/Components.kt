package com.appdate.marvelcomicsinfo

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.appdate.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme

@Composable
fun ComicCard(
    modifier: Modifier,
    imageUrl: String,
    title: String,
    description: String?,
    onComicClick: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val context = LocalContext.current

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .size(Size.ORIGINAL)
                    .build(),
            )
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onComicClick() },
                painter = painter,
                contentDescription = title,
                contentScale = ContentScale.Crop,
            )

            val expanded = remember { mutableStateOf(false) }
            val hasDescription = !description.isNullOrEmpty()
            Text(
                text = if(!hasDescription) title else "$title *",
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        expanded.value = !expanded.value
                    },
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )

            Box(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {
                if (expanded.value && hasDescription) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = description!!,
                        Modifier.padding(10.dp),
                        color = MaterialTheme.colors.onSurface,
                    )
                }
            }
        }
    }
}

@Composable
fun TextLabel(
    text: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular),
            )
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        textAlign = textAlign
    )
}

@Composable
fun ComicTextInfo(label: String, info: String) {
    val fontFamily = FontFamily(Font(R.font.roboto_condensed_regular))
    TextLabel(text = label)
    CompositionLocalProvider( LocalContentAlpha provides 0.7f) {
        Text(
            text = info,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = fontFamily,
            ),

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComicTextsPreview() {
    Column {
        ComicTextInfo(label = "Label:", "info")
        Spacer(modifier = Modifier.height(2.dp))
        ComicTextInfo(label = "Label:", "info")
    }
}

@Preview(showBackground = true)
@Composable
fun ComicCardPreview() {
    MarvelComicsInfoTheme {
        ComicCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(8.dp),
            imageUrl = "hl.us/u/prod/marvel/i/mg/1/00/51644d6b47668.jpg",
            title = "Titulo",
            description = "description"
        ) { }
    }
}