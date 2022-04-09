package com.appdate.marvelcomicsinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.appdate.marvelcomicsinfo.data.local.MarvelDatabase
import com.appdate.marvelcomicsinfo.data.local.dao.MarvelComicDao
import com.appdate.marvelcomicsinfo.repository.ComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(
    private val repository: ComicRepository,
    private val db: MarvelDatabase

): ViewModel() {

    val comics = liveData(Dispatchers.IO){
        val retrievedComics = repository.getComics()

        emit(retrievedComics)
    }

    val dbComics = repository.getAllComics()
}