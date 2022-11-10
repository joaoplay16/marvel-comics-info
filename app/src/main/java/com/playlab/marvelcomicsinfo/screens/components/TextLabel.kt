package com.playlab.marvelcomicsinfo.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun TextLabel(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 30.sp,
            fontFamily = FontFamily(
                Font(R.font.roboto_condensed_regular),
            )
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        textAlign = textAlign
    )
}