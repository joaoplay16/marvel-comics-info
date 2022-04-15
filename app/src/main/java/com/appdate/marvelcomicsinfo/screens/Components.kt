package com.appdate.marvelcomicsinfo.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appdate.marvelcomicsinfo.R
import com.appdate.marvelcomicsinfo.ui.theme.MarvelComicsInfoTheme
import com.appdate.marvelcomicsinfo.ui.theme.topAppBarContentColor

@Composable
fun AppTopAppBar(
    title: String? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    return TopAppBar(
        title = { Text(text = title ?: stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = navigationIcon,
        actions = actions
    )
}

@Composable
fun SearchWidget(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .semantics {
                contentDescription = "SearchWidget"
            },
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "TextField"
                },
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(alpha = ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(alpha = ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .semantics {
                            contentDescription = "CloseButton"
                        },
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.topAppBarContentColor
            )
        )
    }
}

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
        modifier = modifier,
        elevation = elevation,
        backgroundColor = MaterialTheme.colors.surface
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
            }else {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun CardTitle(text: String) {
    Text(
        text = text,
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 4.dp, end = 4.dp)
          ,
        color = MaterialTheme.colors.onSurface,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.roboto_condensed_regular),
            )
        )
    )
}

@Composable
fun TextLabel(
    text: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 30.sp,
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
fun TextInfo(info: String) {
    val fontFamily = FontFamily(Font(R.font.roboto_condensed_regular))
    CompositionLocalProvider( LocalContentAlpha provides 0.7f) {
        Text(
            text = info.capitalize(Locale("pt-BR")),
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

@Composable
fun Copyright(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        color = MaterialTheme.colors.onSurface,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.overline
    )
}

@Preview(showBackground = true)
@Composable
fun ComicTextsPreview() {
    Column {
        TextInfo("info")
        Spacer(modifier = Modifier.height(2.dp))
        TextInfo("info")
    }
}

@Preview(showBackground = true)
@Composable
fun ComicCardPreview() {
    MarvelComicsInfoTheme {
        ComicCard(
            modifier = Modifier
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