package com.appdate.marvelcomicsinfo.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.appdate.marvelcomicsinfo.data.local.MarvelDatabase
import com.appdate.marvelcomicsinfo.data.paging.MarvelRemoteMediator
import com.appdate.marvelcomicsinfo.data.paging.SearchPagingSource
import com.appdate.marvelcomicsinfo.data.preferences.PreferencesDataStore
import com.appdate.marvelcomicsinfo.data.remote.ApiInterface
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class ComicRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val marvelDatabase: MarvelDatabase,
    private val dataStore: PreferencesDataStore
){

    fun getAllComics(): Flow<PagingData<Comic>> {
        val pagingSourceFactory = { marvelDatabase.marvelComicDao().getAllComics() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = MarvelRemoteMediator(
                apiInterface = apiInterface,
                marvelDatabase = marvelDatabase,
                dataStore = dataStore,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun searchComics(query: String): Flow<PagingData<Comic>> {
        return Pager(
            config = PagingConfig(
                pageSize = ITEMS_PER_PAGE,
            ),
            pagingSourceFactory = {
                SearchPagingSource(apiInterface = apiInterface, query = query)
            }
        ).flow
    }
}