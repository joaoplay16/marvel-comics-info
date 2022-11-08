package com.playlab.marvelcomicsinfo.data.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.google.common.truth.Truth
import com.playlab.PagingDataTestUtils
import com.playlab.PagingDataTestUtils.Companion.myHelperTransformFunction
import com.playlab.marvelcomicsinfo.MainCoroutineRule
import com.playlab.stubs.ComicsStub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PagingDataTrasformTest {

    @get:Rule
    var instantTaskExecutorRule =  InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `transform paging data, returns items`() = runTest {

        val differ = AsyncPagingDataDiffer(
            diffCallback = PagingDataTestUtils.MyDiffCallback(),
            updateCallback = PagingDataTestUtils.NoopListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        val pagingData = PagingData.from(ComicsStub.comics)

        differ.submitData(pagingData.myHelperTransformFunction("Hulk"))

        println("DATA ${differ.snapshot().items}")

        advanceUntilIdle()

        Truth.assertThat(differ.snapshot().items).isNotEmpty()
    }
}