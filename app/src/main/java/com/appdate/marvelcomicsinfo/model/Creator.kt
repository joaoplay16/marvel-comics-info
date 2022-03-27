package com.appdate.retrofit

data class Creator (
    val name: String,
    val role: String


){
    override fun toString(): String {
        return "$name - $role"
    }
}
