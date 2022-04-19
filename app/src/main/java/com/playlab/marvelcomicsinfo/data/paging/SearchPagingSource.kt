package com.playlab.marvelcomicsinfo.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.playlab.marvelcomicsinfo.data.remote.ApiInterface
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.util.getOffsetByPage

class SearchPagingSource(
    private val apiInterface: ApiInterface,
    private val query: String
) : PagingSource<Int, Comic>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comic> {
        val currentPage = params.key ?: 1
        Log.d("SearchPagingSource", "params.key ${params.key} size ${params.loadSize} placeholder ${params.placeholdersEnabled}")

        return try {
            val response = apiInterface.searchComics(query = query, offset = getOffsetByPage(currentPage)).data?.results
            val endOfPaginationReached = response == null || response.isEmpty()
            Log.d("SearchPagingSource", "$endOfPaginationReached")
            if (response != null && response.isNotEmpty()) {
                LoadResult.Page(
                    data = response,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Comic>): Int? {
        return state.anchorPosition
    }

}

