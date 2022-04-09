package com.appdate.marvelcomicsinfo.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.appdate.marvelcomicsinfo.data.local.MarvelDatabase
import com.appdate.marvelcomicsinfo.data.remote.ApiInterface
import com.appdate.marvelcomicsinfo.data.remote.MarvelRemoteMediator
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.model.ComicResponse
import com.appdate.marvelcomicsinfo.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ComicRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val marvelDatabase: MarvelDatabase
){

    suspend fun getComics(): ComicResponse {
        return apiInterface.getComics(offset = 0)
    }


    @OptIn(ExperimentalPagingApi::class)
    fun getAllComics(): Flow<PagingData<Comic>> {
        val pagingSourceFactory = { marvelDatabase.marvelComicDao().getAllComics() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = MarvelRemoteMediator(
                apiInterface = apiInterface,
                marvelDatabase = marvelDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}