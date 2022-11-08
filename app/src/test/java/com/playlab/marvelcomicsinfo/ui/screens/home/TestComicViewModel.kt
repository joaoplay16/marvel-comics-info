package com.playlab.marvelcomicsinfo.ui.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.playlab.PagingDataTestUtils
import com.playlab.marvelcomicsinfo.MainCoroutineRule
import com.playlab.marvelcomicsinfo.repository.FakeComicRepository
import com.playlab.marvelcomicsinfo.screens.home.ComicsViewModel
import com.playlab.stubs.ComicsStub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(
    ExperimentalPagingApi::class,
    ExperimentalCoroutinesApi::class)
class TestComicViewModel {

    @get:Rule
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `get all comics, returns valid data`() = runTest {

        val comicsViewModel = ComicsViewModel(FakeComicRepository())

        val pagingData = comicsViewModel.dbComics.first()

        assertThat(pagingData).isInstanceOf(PagingData::class.java)
    }

    @Test
    fun `get all comics, returns paging items`() = runTest {

        val differ = AsyncPagingDataDiffer(
            diffCallback = PagingDataTestUtils.MyDiffCallback(),
            updateCallback = PagingDataTestUtils.NoopListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        val fakeComicRepository = FakeComicRepository()
        fakeComicRepository.pagingData = PagingData.from(ComicsStub.comics)

        val comicsViewModel = ComicsViewModel(fakeComicRepository)

        differ.submitData(comicsViewModel.dbComics.first())

        val comicItems = differ.snapshot().items

        assertThat(comicItems).isNotEmpty()
        assertThat(comicItems).isEqualTo(ComicsStub.comics)
    }
}