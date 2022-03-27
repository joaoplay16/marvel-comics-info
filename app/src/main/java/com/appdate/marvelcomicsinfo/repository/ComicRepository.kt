package com.appdate.marvelcomicsinfo.repository

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appdate.marvelcomicsinfo.util.RetrofitService
import com.appdate.retrofit.ComicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class ComicRepository {


  /*  fun getComics() : LiveData<ComicResponse>{
        val comicsLiveResponse = MutableLiveData<ComicResponse>()

        val apiInterface = RetrofitService.getInterface().getComics()
            apiInterface.enqueue( object : Callback<ComicResponse> {

                override fun onResponse(call: Call<ComicResponse>, response: Response<ComicResponse>) {

                    if(response.isSuccessful){
                        comicsLiveResponse.value = response.body()!!
//                        Log.d("APIINTERFACER", "${comicsResponse.copyright}")


                    }
                }

                override fun onFailure(call: Call<ComicResponse>?, t: Throwable?) {
                    Log.d("APIINTERFACER", "ERRO ${t}")
                }
            })

        return comicsLiveResponse
        }*/

    suspend fun getComics(): ComicResponse {
        val apiInterface = RetrofitService.getInterface()
        return apiInterface.getComics()
    }

}