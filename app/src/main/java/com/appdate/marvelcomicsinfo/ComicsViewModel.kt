package com.appdate.marvelcomicsinfo

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.appdate.marvelcomicsinfo.repository.ComicRepository
import com.appdate.marvelcomicsinfo.util.RetrofitService
import com.appdate.retrofit.Comic
import com.appdate.retrofit.ComicResponse
import com.appdate.retrofit.Data
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicsViewModel: ViewModel() {
    private val repository: ComicRepository= ComicRepository()


    val comics = liveData(Dispatchers.IO){
        val retrievedComics = repository.getComics()
        emit(retrievedComics)
    }


}