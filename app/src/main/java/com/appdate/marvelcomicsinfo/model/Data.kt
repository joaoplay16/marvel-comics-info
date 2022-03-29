package com.appdate.marvelcomicsinfo.model

import com.appdate.marvelcomicsinfo.model.Comic
import com.google.gson.annotations.SerializedName

data class Data(
    val limit: Int,
    @SerializedName("results")
    val results: List<Comic>? = null
){

}

