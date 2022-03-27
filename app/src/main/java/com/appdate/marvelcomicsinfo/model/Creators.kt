package com.appdate.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Creators (
    @SerializedName("items")
    @Expose
    val creatorsItems: List<Creator>){

}