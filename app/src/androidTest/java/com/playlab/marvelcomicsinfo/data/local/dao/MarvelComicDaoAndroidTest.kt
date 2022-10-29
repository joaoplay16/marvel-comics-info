package com.playlab.marvelcomicsinfo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.playlab.marvelcomicsinfo.model.Comic

@Dao
interface MarvelComicDaoAndroidTest {
    @Query("SELECT * FROM marvel_comic_table")
    fun getAllComics(): List<Comic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComics(comics: List<Comic>)

    @Query("DELETE FROM marvel_comic_table")
    suspend fun deleteAllComics()
}