package com.appdate.marvelcomicsinfo.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.appdate.marvelcomicsinfo.data.local.MarvelDatabase
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.model.MarvelRemoteKeys
import com.appdate.marvelcomicsinfo.util.Constants.ITEMS_PER_PAGE

@ExperimentalPagingApi
class MarvelRemoteMediator(
    private val apiInterface: ApiInterface,
    private val marvelDatabase: MarvelDatabase
) : RemoteMediator<Int, Comic>() {

    private val marvelComicDao = marvelDatabase.marvelComicDao()
    private val marvelRemoteKeysDao = marvelDatabase.marvelRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Comic>
    ): MediatorResult {
        return try {
            Log.d("PAGINATION", "LOAD TYPE = $loadType")

            val currentPage = when (loadType) {

                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(10) ?: 10
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = apiInterface.getComics(offset = currentPage, limit = ITEMS_PER_PAGE).data?.results
            val endOfPaginationReached = response!!.isEmpty()
            Log.d("PAGINATION", "endOfPaginationReached = ${endOfPaginationReached}")
            Log.d("PAGINATION", "currentPage = ${currentPage}")

            val prevPage = if (currentPage == 10) null else currentPage - 10
            val nextPage = if (endOfPaginationReached) null else currentPage + 10

            marvelDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    marvelComicDao.deleteAllComics()
                    marvelRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.map { comic ->
                    MarvelRemoteKeys(
                        id = comic.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                marvelRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                marvelComicDao.addComics(comics = response)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Comic>
    ): MarvelRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                marvelRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Comic>
    ): MarvelRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { comic ->
                marvelRemoteKeysDao.getRemoteKeys(id = comic.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Comic>
    ): MarvelRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { comic ->
                marvelRemoteKeysDao.getRemoteKeys(id = comic.id)
            }
    }

}