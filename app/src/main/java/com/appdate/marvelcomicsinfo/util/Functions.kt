package com.appdate.marvelcomicsinfo.util

fun getOffsetByPage(page: Int): Int{
    return if(page == 1) 0 else (page-1) * Constants.ITEMS_PER_PAGE
}