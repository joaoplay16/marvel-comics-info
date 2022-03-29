package com.appdate.marvelcomicsinfo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Creator (
    val name: String,
    val role: String


): Parcelable{
    override fun toString(): String {
        return "$name - $role"
    }
}
