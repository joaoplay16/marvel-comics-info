package com.appdate.marvelcomicsinfo.repository

import com.appdate.marvelcomicsinfo.util.RetrofitService
import com.appdate.marvelcomicsinfo.model.ComicResponse

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