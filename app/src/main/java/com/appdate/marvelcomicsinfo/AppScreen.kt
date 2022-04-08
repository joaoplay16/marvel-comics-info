package com.appdate.marvelcomicsinfo

import java.lang.IllegalArgumentException

enum class AppScreen {
    Series,
    SerieDetails;

    companion object {}
    fun fromRoute(route: String?): AppScreen =
        when (route?.substringBefore("/")){
            Series.name -> Series
            SerieDetails.name -> SerieDetails
            null -> Series
            else -> throw IllegalArgumentException("Route $route is not recognized.")
        }
}