package com.appdate.marvelcomicsinfo.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Parcelize
class Comic (
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("thumbnail")
    @Expose
    val thumbnail: Thumbnail,
    @SerializedName("description")
    @Expose
    val description: String?,
    @SerializedName("creators")
    @Expose
    val creators: Creators?
): Parcelable {
    override fun toString(): String {
        return creators?.creatorsItems.toString() + "\n"
    }
}