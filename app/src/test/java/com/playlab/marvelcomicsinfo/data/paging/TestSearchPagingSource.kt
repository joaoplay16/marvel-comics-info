package com.playlab.marvelcomicsinfo.data.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.playlab.marvelcomicsinfo.MainCoroutineRule
import com.playlab.marvelcomicsinfo.data.remote.ApiInterface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

class TestSearchPagingSource {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    lateinit var api: ApiInterface

    lateinit var searchPagingSource: SearchPagingSource

}