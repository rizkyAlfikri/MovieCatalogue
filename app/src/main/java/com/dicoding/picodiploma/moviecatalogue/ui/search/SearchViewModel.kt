package com.dicoding.picodiploma.moviecatalogue.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository

class SearchViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val query = MutableLiveData<String>()

    fun setQuery(keyWord: String) {
        query.value = keyWord
    }

    val getSearchData = Transformations.switchMap(query) {
        mainRepository.getSearchData(it)
    }

}