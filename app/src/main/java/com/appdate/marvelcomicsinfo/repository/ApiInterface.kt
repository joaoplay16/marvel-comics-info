package com.appdate.marvelcomicsinfo.repository

import com.appdate.marvelcomicsinfo.util.RetrofitService
import com.appdate.marvelcomicsinfo.model.ComicResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

  /*      @GET("series")
        fun getComics(
            @Query("ts") ts: String = RetrofitService.ts,
            @Query("apikey") apiKey: String = RetrofitService.API_KEY,
            @Query("hash") hash: String = RetrofitService.hash(),
            @Query("limit") limit: String = RetrofitService.limit

        ) : Call<ComicResponse>*/

    @GET("series")
    suspend fun getComics(
        @Query("ts") ts: String = RetrofitService.ts,
        @Query("apikey") apiKey: String = RetrofitService.API_KEY,
        @Query("hash") hash: String = RetrofitService.hash(),
        @Query("limit") limit: String = RetrofitService.limit

    ) : ComicResponse
}