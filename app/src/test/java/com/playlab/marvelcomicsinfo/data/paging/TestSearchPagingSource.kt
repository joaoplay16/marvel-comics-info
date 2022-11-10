package com.playlab.marvelcomicsinfo.data.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.playlab.marvelcomicsinfo.MainCoroutineRule
import com.playlab.marvelcomicsinfo.data.remote.ApiInterface
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.model.ComicResponse
import com.playlab.marvelcomicsinfo.model.Data
import com.playlab.marvelcomicsinfo.util.Constants
import com.playlab.stubs.ComicsStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
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
            data = Data(
                limit = Constants.ITEMS_PER_PAGE,
                results = ComicsStub.comics
            )
        )
    }

    @Before
    fun setup() {
        searchPagingSource = SearchPagingSource(api, "")
    }

    @Test
    fun `search paging source load - failure - exception thrown` () = runTest {
        val error = NullPointerException()

        given(api.searchComics(  any(), any(), any(), any(), any(), any(), any() ))
            .willThrow(error)

        val expectedResult = PagingSource.LoadResult.Error<Int, Comic>(error)

        assertThat(
            searchPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        ).isEqualTo(expectedResult)
    }

    @Test
    fun `search paging source load - failure - returns loading error` () = runTest {
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
        given(api.searchComics(
            any(), any(), any(), any(), any(), any(), any()
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

    @Test
    fun `search paging source refresh - success` () = runTest {

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

    @Test
    fun `search paging source append - success` () = runTest {

        given(api.searchComics(
            any(), any(), any(), any(), any(), any(), any()
        )
        ).willReturn(searchResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = searchResponse.data!!.results!!,
            prevKey = 1,
            nextKey = 3
        )

        val result = searchPagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `search paging source prepend - success` () = runTest {

        given(api.searchComics(
            any(), any(), any(), any(), any(), any(), any()
        )
        ).willReturn(searchResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = searchResponse.data!!.results!!,
            prevKey = 9,
            nextKey = 11
        )

        val result = searchPagingSource.load(
            PagingSource.LoadParams.Prepend(
                key = 10,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertThat(result).isEqualTo(expectedResult)
    }

}