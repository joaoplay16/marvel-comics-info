package com.appdate.marvelcomicsinfo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appdate.marvelcomicsinfo.model.MarvelRemoteKeys

@Dao
interface MarvelRemoteKeysDao {

    @Query("SELECT * FROM marvel_remote_keys_table WHERE id =:id")
    suspend fun getRemoteKeys(id: String): MarvelRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<MarvelRemoteKeys>)

    @Query("DELETE FROM marvel_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}