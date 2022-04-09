package com.appdate.marvelcomicsinfo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appdate.marvelcomicsinfo.data.local.dao.Converters
import com.appdate.marvelcomicsinfo.data.local.dao.MarvelComicDao
import com.appdate.marvelcomicsinfo.data.local.dao.MarvelRemoteKeysDao
import com.appdate.marvelcomicsinfo.model.Comic
import com.appdate.marvelcomicsinfo.model.MarvelRemoteKeys

@Database(entities = [Comic::class, MarvelRemoteKeys::class], version = 2)
@TypeConverters(Converters::class)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun marvelComicDao(): MarvelComicDao
    abstract fun marvelRemoteKeysDao(): MarvelRemoteKeysDao

}