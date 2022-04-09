package com.appdate.marvelcomicsinfo.util

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

object Constants {
    const val MARVEL_DATABASE = "marvel_database"
    const val MARVEL_COMIC_TABLE = "marvel_comic_table"
    const val MARVEL_REMOTE_KEYS_TABLE = "marvel_remote_keys_table"

    val BASE_URL = "https://gateway.marvel.com/v1/public/"
    val ts = Timestamp(System.currentTimeMillis()).time.toString()
    val API_KEY = "aa0ee75f268fcf31ee28ff428eee0904"
    val PRIVATE_KEY = "d88b9acfc6f346aa7b3abec5788fa478845a9a8b"

    val ITEMS_PER_PAGE = 10




    fun generateMd5Hash(): String{
        val input = "$ts$PRIVATE_KEY$API_KEY"
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }
}