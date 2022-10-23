package com.playlab.marvelcomicsinfo.util

import com.playlab.marvelcomicsinfo.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

object Constants {
    const val MARVEL_DATABASE = "marvel_database"
    const val MARVEL_COMIC_TABLE = "marvel_comic_table"
    const val MARVEL_REMOTE_KEYS_TABLE = "marvel_remote_keys_table"

    const val BASE_URL = "https://gateway.marvel.com/v1/public/"
    val ts = Timestamp(System.currentTimeMillis()).time.toString()
    const val API_KEY = BuildConfig.API_KEY
    const val PRIVATE_KEY = BuildConfig.PRIVATE_KEY

    const val ITEMS_PER_PAGE = 10

    const val COMIC_NAV_KEY = "comic"

    fun generateMd5Hash(): String{
        val input = "$ts$PRIVATE_KEY$API_KEY"
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }
}