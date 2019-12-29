package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMovieViewModel(
    private val mainRepository: MainRepository
) :
    ViewModel() {

    private val movieId = MutableLiveData<Int>()

    fun setIdMovie(id: Int) {
        movieId.value = id
    }

    val getMovieDetail = Transformations.switchMap(movieId){
        mainRepository.getDetailMovieData(it)
    }

    val getMovieFavoriteById = Transformations.switchMap(movieId){
        mainRepository.getMovieFavoriteById(it)
    }

    fun insertMovieFavorite(data: MoviePopularEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertMovieFavorite(data)
        }
    }

    fun deleteMovieFavorite(data: MoviePopularEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteMovieFavorite(data)
        }
    }

}