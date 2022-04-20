package com.playlab.marvelcomicsinfo.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.playlab.marvelcomicsinfo.data.local.MarvelDatabase
import com.playlab.marvelcomicsinfo.data.paging.MarvelRemoteMediator
import com.playlab.marvelcomicsinfo.data.paging.SearchPagingSource
import com.playlab.marvelcomicsinfo.data.preferences.PreferencesDataStore
import com.playlab.marvelcomicsinfo.data.remote.ApiInterface
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.util.Constants.ITEMS_PER_PAGE
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