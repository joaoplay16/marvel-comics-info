package com.appdate.marvelcomicsinfo.model

import com.google.gson.annotations.SerializedName

data class Data(
    val limit: Int,
    val results: List<Comic>? = null
){

}

