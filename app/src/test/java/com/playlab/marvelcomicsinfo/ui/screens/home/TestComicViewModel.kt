package com.playlab.marvelcomicsinfo.ui.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.playlab.PagingDataTestUtils
import com.playlab.marvelcomicsinfo.MainCoroutineRule
import com.playlab.marvelcomicsinfo.data.paging.PagingDataTrasformTest.Companion.comics
import com.playlab.marvelcomicsinfo.repository.FakeComicRepository
import com.playlab.marvelcomicsinfo.screens.home.ComicsViewModel
import com.playlab.stubs.ComicsStub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
@OptIn(
    ExperimentalPagingApi::class,
    ExperimentalCoroutinesApi::class)
class TestComicViewModel {

    @get:Rule
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var fakeComicRepository: FakeComicRepository



    @Test
    fun `get all comics, returns valid data`() = runTest {

        val comicsViewModel = ComicsViewModel(fakeComicRepository)

        val data = comicsViewModel.dbComics

        assertThat(data.first()).isInstanceOf(PagingData::class.java)
    }

    @Test
    fun `get all comics, returns paging items`() = runTest {

        val differ = AsyncPagingDataDiffer(
            diffCallback = PagingDataTestUtils.MyDiffCallback(),
            updateCallback = PagingDataTestUtils.NoopListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        given(fakeComicRepository.getAllComics()).willReturn(
            flow { emit(PagingData.from(ComicsStub.comics)) }
        )

        val comicsViewModel = ComicsViewModel(fakeComicRepository)

        differ.submitData(comicsViewModel.dbComics.first())

        val data = differ.snapshot().items

        assertThat(data).isNotEmpty()
        assertThat(data).isEqualTo(comics)
    }
}