package com.playlab.marvelcomicsinfo.screens.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.playlab.marvelcomicsinfo.R

@Composable
fun AppTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    return TopAppBar(
        modifier = modifier,
        title = { Text(text = title ?: stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = navigationIcon,
        actions = actions
    )
}