package com.appdate.marvelcomicsinfo.model

import android.os.Parcelable
import com.appdate.marvelcomicsinfo.model.Creator
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Creators (
    @SerializedName("items")
    val creatorsItems: List<Creator>): Parcelable