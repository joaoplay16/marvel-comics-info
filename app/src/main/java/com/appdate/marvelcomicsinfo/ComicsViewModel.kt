package com.appdate.marvelcomicsinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.appdate.marvelcomicsinfo.repository.ComicRepository
import kotlinx.coroutines.Dispatchers

class ComicsViewModel: ViewModel() {
    private val repository: ComicRepository= ComicRepository()

    val comics = liveData(Dispatchers.IO){
        val retrievedComics = repository.getComics()
        emit(retrievedComics)
    }
}