package com.playlab.marvelcomicsinfo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.playlab.marvelcomicsinfo.data.local.dao.Converters
import com.playlab.marvelcomicsinfo.data.local.dao.MarvelComicDao
import com.playlab.marvelcomicsinfo.data.local.dao.MarvelRemoteKeysDao
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.model.MarvelRemoteKeys

@Database(entities = [Comic::class, MarvelRemoteKeys::class], version = 2)
@TypeConverters(Converters::class)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun marvelComicDao(): MarvelComicDao
    abstract fun marvelRemoteKeysDao(): MarvelRemoteKeysDao

}