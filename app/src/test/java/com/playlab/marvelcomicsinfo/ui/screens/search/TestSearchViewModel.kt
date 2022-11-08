package com.playlab.marvelcomicsinfo.ui.screens.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.playlab.marvelcomicsinfo.MainCoroutineRule
import com.playlab.marvelcomicsinfo.repository.FakeComicRepository
import com.playlab.marvelcomicsinfo.screens.home.ComicsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(
    ExperimentalPagingApi::class,
    ExperimentalCoroutinesApi::class)
class TestSearchViewModel {

    @get:Rule
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Test
    fun `search comics, returns valid data`() = runTest {

        val comicsViewModel = ComicsViewModel(FakeComicRepository())

        val data = comicsViewModel.dbComics

        assertThat(data.first()).isInstanceOf(PagingData::class.java)
    }
}