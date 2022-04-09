package com.appdate.marvelcomicsinfo.data.remote

import com.appdate.marvelcomicsinfo.model.ComicResponse
import com.appdate.marvelcomicsinfo.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("series")
    suspend fun getComics(
        @Query("ts") ts: String = Constants.ts,
        @Query("apikey") apiKey: String = Constants.API_KEY,
        @Query("hash") hash: String = Constants.generateMd5Hash(),
        @Query("limit") limit: Int = Constants.ITEMS_PER_PAGE,
        @Query("offset") offset: Int = 0
    ) : ComicResponse
}