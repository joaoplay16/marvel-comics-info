package com.appdate.marvelcomicsinfo.util

import com.appdate.marvelcomicsinfo.repository.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class RetrofitService {
    companion object {
        val BASE_URL = "https://gateway.marvel.com/v1/public/"
        val ts = Timestamp(System.currentTimeMillis()).time.toString()
        val API_KEY = "aa0ee75f268fcf31ee28ff428eee0904"
        val PRIVATE_KEY = "d88b9acfc6f346aa7b3abec5788fa478845a9a8b"
        val limit = "100"

        fun hash(): String{
            val input = "$ts$PRIVATE_KEY$API_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray()))
                .toString(16)
                .padStart(32, '0')
        }

        fun getInterface(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }


}