package com.playlab.marvelcomicsinfo.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.playlab.marvelcomicsinfo.model.Comic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@ExperimentalPagingApi
class FakeComicRepositoryAndroidTest: ComicRepository {

    private val pagingComicItems = MutableStateFlow<PagingData<Comic>>(PagingData.empty())

    override fun getAllComics(): Flow<PagingData<Comic>> {
        return pagingComicItems
    }

    override fun searchComics(query: String): Flow<PagingData<Comic>> {
        return MutableStateFlow(PagingData.empty())
    }
}