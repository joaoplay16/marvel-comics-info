package com.playlab.marvelcomicsinfo.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.playlab.marvelcomicsinfo.R

@Composable
fun CopyrightContainer(
    modifier: Modifier = Modifier,
    copyright: String?,
    content: @Composable (Modifier) -> Unit,
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            content(Modifier)
        }
        copyright?.let {
            Copyright(
                modifier = Modifier
                    .background(MaterialTheme.colors.onPrimary),
                text = stringResource(
                    R.string.data_provided,
                    copyright
                )
            )
        }
    }
}