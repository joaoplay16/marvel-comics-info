package com.playlab.marvelcomicsinfo.data.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.playlab.marvelcomicsinfo.MainCoroutineRule
import com.playlab.marvelcomicsinfo.data.remote.ApiInterface
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.model.ComicResponse
import com.playlab.marvelcomicsinfo.model.Data
import com.playlab.marvelcomicsinfo.model.Thumbnail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
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

    @Test
    fun `reviews paging source load - failure - exception thrown` () = runTest {
       doThrow(NullPointerException()).`when`(api).getComics()

//        willThrow(NullPointerException()).given(
//            api
//        ).getComics()
    }

    @Test
    fun `reviews paging source load - failure - returns loading error` () = runTest {
        assertThat(
            searchPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        ).isInstanceOf(PagingSource.LoadResult.Error::class.java)
    }

    @Test
    fun `search paging source load - failure - received null` () = runTest {
        given(api.getComics(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.anyString()
        )).willReturn(null)

        val expectedResult = PagingSource.LoadResult.Error<Int, Comic>(NullPointerException())

        assertThat(searchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )).toString()
        ).isEqualTo(expectedResult.toString())
    }
}