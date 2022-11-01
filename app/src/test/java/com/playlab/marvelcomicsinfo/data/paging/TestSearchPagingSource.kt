package com.playlab.marvelcomicsinfo.data.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.playlab.marvelcomicsinfo.MainCoroutineRule
import com.playlab.marvelcomicsinfo.data.remote.ApiInterface
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.model.ComicResponse
import com.playlab.marvelcomicsinfo.model.Data
import com.playlab.marvelcomicsinfo.model.Thumbnail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@OptIn(ExperimentalCoroutinesApi::class)
class TestSearchPagingSource {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var api: ApiInterface

    private lateinit var searchPagingSource: SearchPagingSource

    companion object {
        val searchResponse = ComicResponse(
            copyright = "",
            data = Data(
                limit =1,
                results = listOf(
                    Comic(id = "id",
                        "titulo",
                        Thumbnail("path", ".jpg"),
                        null, null))
            )
        )

        val nextSearchResponse = ComicResponse(
            copyright = "",
            data = Data(
                limit =1,
                results = listOf()
            )
        )
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        searchPagingSource = SearchPagingSource(api, "")
    }
}