package com.dicoding.picodiploma.moviecatalogue.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvpopularentity.TvPopularEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getAllMovieFavorite() = mainRepository.getFavoriteMovieData()

    fun insertMovieFavorite(data: MoviePopularEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertMovieFavorite(data)
        }
    }

    fun deleteMovieFavoriteById(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteMovieFavoriteById(movieId)
        }
    }

    fun getAllTvFavorite() = mainRepository.getFavoriteTvData()

    fun insertTvFavorite(data: TvPopularEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertTvFavorite(data)
        }
    }

    fun deleteTvFavoriteById(tvId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteTvFavoriteById(tvId)
        }
    }
}