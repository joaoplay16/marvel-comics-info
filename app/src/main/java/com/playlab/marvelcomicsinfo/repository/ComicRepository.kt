package com.playlab.marvelcomicsinfo.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.playlab.marvelcomicsinfo.model.Comic
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
interface ComicRepository  {

    fun getAllComics(): Flow<PagingData<Comic>>

    fun searchComics(query: String): Flow<PagingData<Comic>>
}