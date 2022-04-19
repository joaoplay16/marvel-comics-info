package com.playlab.marvelcomicsinfo.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.playlab.marvelcomicsinfo.model.Comic
import com.playlab.marvelcomicsinfo.repository.ComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ComicRepository
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedComics = MutableStateFlow<PagingData<Comic>>(PagingData.empty())
    val searchedComics = _searchedComics

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchComics(query: String) {
        viewModelScope.launch {
            repository.searchComics(query).cachedIn(viewModelScope).collect{
                _searchedComics.value = it
            }
        }
    }

}