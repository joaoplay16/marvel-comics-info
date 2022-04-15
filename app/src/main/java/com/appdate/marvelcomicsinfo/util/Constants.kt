package com.appdate.marvelcomicsinfo.util

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

object Constants {
    const val MARVEL_DATABASE = "marvel_database"
    const val MARVEL_COMIC_TABLE = "marvel_comic_table"
    const val MARVEL_REMOTE_KEYS_TABLE = "marvel_remote_keys_table"

    const val BASE_URL = "https://gateway.marvel.com/v1/public/"
    val ts = Timestamp(System.currentTimeMillis()).time.toString()
    const val API_KEY = "96a7de2acfe9f0e5142aa404b765ee32"
    const val PRIVATE_KEY = "b7e88947e5515ae21d44d5dcc35dcb81068b63a8"

    const val ITEMS_PER_PAGE = 10

    fun generateMd5Hash(): String{
        val input = "$ts$PRIVATE_KEY$API_KEY"
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }
}