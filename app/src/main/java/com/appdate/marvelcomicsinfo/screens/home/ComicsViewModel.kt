package com.appdate.marvelcomicsinfo.screens.home

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.appdate.marvelcomicsinfo.data.preferences.PreferencesDataStore
import com.appdate.marvelcomicsinfo.repository.ComicRepository
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