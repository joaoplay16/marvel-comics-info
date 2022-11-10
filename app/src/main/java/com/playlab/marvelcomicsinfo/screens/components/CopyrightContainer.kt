package com.playlab.marvelcomicsinfo.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content(Modifier.weight(1f))
        copyright?.let {
            Copyright(text = stringResource(R.string.data_provided, copyright))
        }
    }
}