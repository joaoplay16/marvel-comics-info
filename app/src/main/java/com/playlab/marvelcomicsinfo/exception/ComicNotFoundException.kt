package com.playlab.marvelcomicsinfo.exception

class ComicNotFoundException : Exception() {
    override val cause: Throwable = Throwable("Comic not found")
}