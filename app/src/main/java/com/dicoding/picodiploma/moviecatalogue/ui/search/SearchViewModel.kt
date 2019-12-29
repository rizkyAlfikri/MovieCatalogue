package com.dicoding.picodiploma.moviecatalogue.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val query = MutableLiveData<String>()

    fun setQuery(keyWord: String) {
        query.value = keyWord
    }

    val getSearchData = Transformations.switchMap(query) {
        mainRepository.getSearchData(it)
    }

}