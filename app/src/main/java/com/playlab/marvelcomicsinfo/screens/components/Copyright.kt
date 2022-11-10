package com.playlab.marvelcomicsinfo.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Copyright(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = text,
        color = MaterialTheme.colors.onSurface,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.overline
    )
}