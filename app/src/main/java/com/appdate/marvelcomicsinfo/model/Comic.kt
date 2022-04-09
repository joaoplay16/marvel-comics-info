package com.appdate.marvelcomicsinfo.model
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appdate.marvelcomicsinfo.util.Constants.MARVEL_COMIC_TABLE
import kotlinx.parcelize.Parcelize

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Parcelize
@Entity(tableName = MARVEL_COMIC_TABLE)
class Comic (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    @Embedded
    val thumbnail: Thumbnail,
    val description: String?,
    @Embedded
    val creators: Creators?
): Parcelable {
    override fun toString(): String {
        return creators?.creatorsItems.toString() + "\n"
    }
}