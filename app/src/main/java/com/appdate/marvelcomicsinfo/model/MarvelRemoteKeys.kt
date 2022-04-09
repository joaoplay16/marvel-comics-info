package com.appdate.marvelcomicsinfo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appdate.marvelcomicsinfo.util.Constants.MARVEL_REMOTE_KEYS_TABLE

@Entity(tableName = MARVEL_REMOTE_KEYS_TABLE)
data class MarvelRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)