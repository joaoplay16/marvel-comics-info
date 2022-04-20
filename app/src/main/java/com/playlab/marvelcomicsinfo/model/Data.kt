package com.playlab.marvelcomicsinfo.model

data class Data(
    val limit: Int,
    val results: List<Comic>? = null
){

}

