package com.playlab.marvelcomicsinfo.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.playlab.marvelcomicsinfo.R

@Composable
fun CardTitle(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 4.dp, end = 4.dp),
        color = MaterialTheme.colors.onSurface,
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 22.sp,
            fontFamily = FontFamily(
                Font(R.font.roboto_condensed_regular),
            )
        )
    )
}