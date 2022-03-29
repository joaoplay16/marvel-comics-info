package com.appdate.marvelcomicsinfo

import java.lang.IllegalArgumentException

enum class MarvelScreen {
    Series,
    SingleSerie;

    companion object {}
    fun fromRoute(route: String?): MarvelScreen =
        when (route?.substringBefore("/")){
            Series.name -> Series
            SingleSerie.name -> SingleSerie
            null -> Series
            else -> throw IllegalArgumentException("Route $route is not recognized.")
        }
}