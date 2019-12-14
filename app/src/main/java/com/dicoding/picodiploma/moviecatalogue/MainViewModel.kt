package com.dicoding.picodiploma.moviecatalogue

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.utils.CoroutineContextProvider
import com.dicoding.picodiploma.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    fun getMoviePopularData() = mainRepository.getPopularMovieData()


    fun getTvPopular() = mainRepository.getPopularTvData()


}
