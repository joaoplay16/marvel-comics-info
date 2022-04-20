package com.playlab.marvelcomicsinfo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Creators (
    @SerializedName("items")
    val creatorsItems: List<Creator>): Parcelable