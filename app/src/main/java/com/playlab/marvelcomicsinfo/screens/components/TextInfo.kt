package com.playlab.marvelcomicsinfo.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.playlab.marvelcomicsinfo.R

@Composable
fun TextInfo(
    modifier: Modifier = Modifier,
    info: String,
) {
    val fontFamily = FontFamily(Font(R.font.roboto_condensed_regular))
    CompositionLocalProvider(LocalContentAlpha provides 0.7f) {
        Text(
            text = info.capitalize(Locale("en-US")),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = fontFamily,
            ),

            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        )
    }
}