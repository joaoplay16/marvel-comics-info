package com.playlab.marvelcomicsinfo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.playlab.marvelcomicsinfo.data.local.dao.*
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.model.MarvelRemoteKeys

@Database(entities = [Comic::class, MarvelRemoteKeys::class], version = 2)
@TypeConverters(Converters::class)
abstract class MarvelDatabaseAndroidTest : RoomDatabase(){
    abstract fun marvelComicDao(): MarvelComicDaoAndroidTest
    abstract fun marvelRemoteKeysDao(): MarvelRemoteKeysDao
}