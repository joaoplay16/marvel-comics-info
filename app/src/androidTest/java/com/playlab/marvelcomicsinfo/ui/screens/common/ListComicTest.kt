package com.playlab.marvelcomicsinfo.ui.screens.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.filters.MediumTest
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.screens.common.ListComic
import com.playlab.stubs.ComicsStub
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
class ListComicTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun comicList_haveAtLeastOneChildren() {
        val flowData: Flow<PagingData<Comic>> =
            MutableStateFlow(PagingData.from(ComicsStub.comics))

        val testTag = "ComicList"

        composeTestRule.setContent {
            val items = flowData.collectAsLazyPagingItems()
            ListComic(
                items = items,
                modifier = Modifier.testTag(testTag),
                onComicClick = {})
        }

        composeTestRule.onNodeWithTag(testTag).onChildren().onFirst().assertExists()
    }
}