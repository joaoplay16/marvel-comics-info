package com.appdate.marvelcomicsinfo.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.appdate.marvelcomicsinfo.data.local.MarvelDatabase
import com.appdate.marvelcomicsinfo.data.preferences.PreferencesDataStore
import com.appdate.marvelcomicsinfo.data.remote.ApiInterface
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.model.MarvelRemoteKeys
import com.appdate.marvelcomicsinfo.util.Constants.ITEMS_PER_PAGE
import com.appdate.marvelcomicsinfo.util.getOffsetByPage

@ExperimentalPagingApi
class MarvelRemoteMediator(
    private val apiInterface: ApiInterface,
    private val marvelDatabase: MarvelDatabase,
    private val dataStore: PreferencesDataStore
) : RemoteMediator<Int, Comic>() {

    private val marvelComicDao = marvelDatabase.marvelComicDao()
    private val marvelRemoteKeysDao = marvelDatabase.marvelRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Comic> // All data stored in db
    ): MediatorResult {
        return try {
            Log.d("MEDIATOR", "loadType $loadType")
            val currentPage = when (loadType) {

                /*Generally, this means that a request to load remote data and replace all local data was made.*/
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    //Here there will always be an item, because it was already saved when in REFRESH.
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            /*if false no data is saved in the local database, requests data from the network.
                            * if true has reached the end of the page, stops placing items at the beginning and
                            * goes to the next loadType.
                            * */
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    //Here there will always be an item, because it was already saved when in REFRESH.
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    Log.d("MEDIATOR", "loadType $remoteKeys")

                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = apiInterface.getComics(
                offset = getOffsetByPage(currentPage),
                limit = ITEMS_PER_PAGE
            )

            val comics = response.data!!.results
            val endOfPaginationReached = comics!!.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            marvelDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    //initial load, clear all data from db
                    marvelComicDao.deleteAllComics()
                    marvelRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = comics.map { comic ->
                    MarvelRemoteKeys(
                        id = comic.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                dataStore.saveCopyright(response.copyright)

                marvelRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                marvelComicDao.addComics(comics = comics)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Comic>
    ): MarvelRemoteKeys? {
        Log.d("MEDIATOR", "state.anchorPosition ${state.anchorPosition}")

        return state.anchorPosition?.let { position ->
            Log.d("MEDIATOR", "position $position")
            state.closestItemToPosition(position)?.id?.let { id ->

                marvelRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Comic>
    ): MarvelRemoteKeys? {
        return state.pages.firstOrNull{it.data.isNotEmpty()}?.data?.firstOrNull()
            ?.let { comic ->
                marvelRemoteKeysDao.getRemoteKeys(id = comic.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Comic>
    ): MarvelRemoteKeys? {
        return state.pages.lastOrNull{it.data.isNotEmpty()}?.data?.lastOrNull()
            ?.let { comic ->
                marvelRemoteKeysDao.getRemoteKeys(id = comic.id)
            }
    }

}