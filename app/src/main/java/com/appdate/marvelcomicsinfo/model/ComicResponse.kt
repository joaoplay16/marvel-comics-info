package com.appdate.marvelcomicsinfo.model

import com.appdate.marvelcomicsinfo.model.Data
import com.google.gson.annotations.SerializedName

class ComicResponse (
    val copyright: String,
    @SerializedName("data")
    val data: Data? = null
)