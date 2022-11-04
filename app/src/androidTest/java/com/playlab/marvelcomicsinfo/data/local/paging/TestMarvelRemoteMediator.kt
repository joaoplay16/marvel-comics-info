package com.playlab.marvelcomicsinfo.data.local.paging

import androidx.paging.*
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.RemoteMediator.MediatorResult
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.playlab.marvelcomicsinfo.data.local.MarvelDatabase
import com.playlab.marvelcomicsinfo.data.paging.MarvelRemoteMediator
import com.playlab.marvelcomicsinfo.data.remote.ApiInterface
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.model.ComicResponse
import com.playlab.marvelcomicsinfo.model.Data
import com.playlab.marvelcomicsinfo.model.Thumbnail
import com.playlab.marvelcomicsinfo.util.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import javax.inject.Inject
import javax.inject.Named

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@MediumTest
@RunWith(MockitoJUnitRunner::class)
class TestMarvelRemoteMediator {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Mock
    private lateinit var api: ApiInterface

    @Inject
    @Named("test_paging_db")
    lateinit var db: MarvelDatabase

    @Before
    fun before(){
        hiltRule.inject()
    }

    @After
    fun tearDown(){
        db.close()
    }

    companion object {
        val comicResponse = ComicResponse(
            copyright = "",
            data = Data(
                limit = Constants.ITEMS_PER_PAGE,
                results = listOf(
                    Comic(id = "01",
                        "title 1",
                        Thumbnail("path", ".jpg"),
                        null, null),
                )
            )
        )

        val emptyComicResponse = ComicResponse(
            copyright = "",
            data = Data(
                limit = Constants.ITEMS_PER_PAGE,
                results = listOf()
            )
        )
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
       given( api.getComics(any(), any(), any(), any(), any(), any()) )
        .willReturn(comicResponse)

        val remoteMediator = MarvelRemoteMediator(
            api,
            db
        )

        val pagingState = PagingState<Int, Comic>(
            pages = listOf<Page<Int, Comic>>(),
            anchorPosition = null,
            config =  PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertThat( result is MediatorResult.Success ).isTrue()
        assertThat( ( result as MediatorResult.Success ).endOfPaginationReached ).isFalse()
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        given( api.getComics(any(), any(), any(), any(), any(), any()) )
            .willReturn(emptyComicResponse)

        val remoteMediator = MarvelRemoteMediator(
            api,
            db
        )

        val pagingState = PagingState<Int, Comic>(
            pages = listOf<Page<Int, Comic>>(),
            anchorPosition = null,
            config =  PagingConfig(10),
            leadingPlaceholderCount = 10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertThat( result is MediatorResult.Success ).isTrue()
        assertThat( ( result as MediatorResult.Success ).endOfPaginationReached ).isTrue()
    }
}