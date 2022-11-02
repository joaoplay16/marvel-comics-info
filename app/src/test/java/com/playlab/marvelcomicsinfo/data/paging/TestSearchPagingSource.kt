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
import com.playlab.marvelcomicsinfo.util.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.doThrow
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any

@RunWith(MockitoJUnitRunner::class)
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
                limit = Constants.ITEMS_PER_PAGE,
                results = listOf(
                    Comic(id = "01",
                        "title 1",
                        Thumbnail("path", ".jpg"),
                        null, null))
            )
        )
    }

    @Before
    fun setup() {
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

    @Test
    fun `search paging source load - success` () = runTest {

        given(api.searchComics(
                any(), any(), any(), any(), any(), any(), any()
            )
        ).willReturn(searchResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = searchResponse.data!!.results!!,
            prevKey = null,
            nextKey = 2
        )

        val result = searchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertThat(result).isEqualTo(expectedResult)
    }

}