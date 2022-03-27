package com.appdate.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Comic (
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("thumbnail")
    @Expose
    var thumbnail: Thumbnail,
    @SerializedName("description")
    @Expose
    var description: String,
    @SerializedName("creators")
    @Expose
    var creators: Creators
        ): Serializable {

    override fun toString(): String {
        return creators.creatorsItems.toString() + "\n"
    }
        }