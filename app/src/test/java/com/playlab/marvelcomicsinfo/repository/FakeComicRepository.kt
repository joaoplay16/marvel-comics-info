package com.playlab.marvelcomicsinfo.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.playlab.marvelcomicsinfo.model.Comic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalPagingApi::class)
open class FakeComicRepository : ComicRepository {

    var pagingData = PagingData.empty<Comic>()

    override fun getAllComics(): Flow<PagingData<Comic>> {
        return MutableStateFlow(pagingData)
    }

    override fun searchComics(query: String): Flow<PagingData<Comic>> {
       return MutableStateFlow(pagingData)
    }
}