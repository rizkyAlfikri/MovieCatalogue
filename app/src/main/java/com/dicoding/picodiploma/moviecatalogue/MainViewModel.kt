package com.dicoding.picodiploma.moviecatalogue

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.utils.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val movieSortBy = MutableLiveData<String>()
    private val tvSortBy = MutableLiveData<String>()
    var movieSpinnerPosition = 0
    var tvSpinnerPosition = 0

    fun setMovieSpinner() {
        when (movieSpinnerPosition) {
            0 -> movieSortBy.value = Config.POPULAR
            1 -> movieSortBy.value = Config.TOP_RATED
            2 -> movieSortBy.value = Config.UPCOMING_MOVIE
        }
    }

    fun setTvSpinner() {
        when(tvSpinnerPosition) {
            0 -> tvSortBy.value = Config.POPULAR
            1 -> tvSortBy.value = Config.TOP_RATED
            2 -> tvSortBy.value = Config.ON_THE_AIR_TV
        }
    }

    val getMoviePopularData = Transformations.switchMap(movieSortBy) {
        mainRepository.getPopularMovieData(it)
    }

    fun insertMovieFavorite(moviePopularEntity: MoviePopularEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertMovieFavorite(moviePopularEntity)
        }
    }

    fun deleteMovieFavoriteById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e(MainViewModel::class.java.simpleName, "Delete Movie Query")
            mainRepository.deleteMovieFavoriteById(movieId)
        }
    }

    val getTvPopularData = Transformations.switchMap(tvSortBy) {
        mainRepository.getPopularTvData(it)
    }

    fun insertTvPopular(tvPopularEntity: TvPopularEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertTvFavorite(tvPopularEntity)
        }
    }

    fun deleteTvFavoriteById(tvId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteTvFavoriteById(tvId)
        }
    }
}
