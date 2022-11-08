package com.playlab.marvelcomicsinfo.ui.screens.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.playlab.marvelcomicsinfo.MainCoroutineRule
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.repository.FakeComicRepository
import com.playlab.marvelcomicsinfo.screens.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
@OptIn(
    ExperimentalPagingApi::class,
    ExperimentalCoroutinesApi::class)
class TestSearchViewModel {

    @get:Rule
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var fakeComicRepository: FakeComicRepository

    @Test
    fun `search comic with empty query string, returns valid empty data`() = runTest {

        val flowPagingData = MutableStateFlow<PagingData<Comic>>(PagingData.empty())

        given(fakeComicRepository.searchComics(any())).willReturn(flowPagingData)

        val searchViewModel = SearchViewModel(fakeComicRepository)
        searchViewModel.searchComics("")

        val result = searchViewModel.searchedComics.firstOrNull()
        assertThat(result).isNotNull()
    }
}