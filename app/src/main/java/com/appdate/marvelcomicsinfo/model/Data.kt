package com.appdate.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Data(
    val limit: Int,
    @SerializedName("results")
    val results: List<Comic>? = null
){

}

