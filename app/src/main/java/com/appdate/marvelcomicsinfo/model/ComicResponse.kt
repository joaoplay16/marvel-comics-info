package com.appdate.retrofit

import com.google.gson.annotations.SerializedName

class ComicResponse (
    val copyright: String,
    @SerializedName("data")
    val data: Data? = null
)