package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie

import androidx.lifecycle.*
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMovieViewModel(
    private val mainRepository: MainRepository
) :
    ViewModel() {

    private var movieId = MutableLiveData<Int>()
    private var isTest = false

    fun setIdMovie(id: Int) {
        movieId.value = id
    }

    fun getMovieDetail() = Transformations.switchMap(movieId){
        mainRepository.getDetailMovieData(it)
    }

    fun getMovieDetailById(id: Int): LiveData<MovieDetailEntity> {
        return mainRepository.getMovieDetailById(id)
    }

    fun insertMovieDetailFavorite(movieDetailEntity: MovieDetailEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertMovieDetailFavorite(movieDetailEntity)
        }
    }

    fun deleteMovieDetailFavorite(movieDetailEntity: MovieDetailEntity) {
        viewModelScope.launch(Dispatchers.IO){
            mainRepository.deleteMovieDetailFavorite(movieDetailEntity)
        }
    }

}