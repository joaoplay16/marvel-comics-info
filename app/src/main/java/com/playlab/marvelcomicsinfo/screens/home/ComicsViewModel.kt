package com.playlab.marvelcomicsinfo.screens.home

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.playlab.marvelcomicsinfo.data.preferences.PreferencesDataStore
import com.playlab.marvelcomicsinfo.repository.ComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ComicsViewModel @Inject constructor(
    repository: ComicRepository,
    dataStore: PreferencesDataStore
): ViewModel() {
    val copyright = dataStore.getCopyright

    val dbComics  = repository.getAllComics()
}