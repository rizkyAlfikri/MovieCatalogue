package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.utils.CoroutineContextProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailTvViewModel(
    private val mainRepository: MainRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        Log.e(DetailTvViewModel::class.java.name, "${throwable.message}")
    }

    private var tvShowId = MutableLiveData<Int>()

    fun setTvId(id: Int) {
        tvShowId.value = id
    }

    val getTvDetail = Transformations.switchMap(tvShowId) {
        mainRepository.getDetailTvData(it)
    }

    val getTvFavoriteById = Transformations.switchMap(tvShowId) {
        mainRepository.getTvFavoriteById(it)
    }

    fun insertTvFavorite(tvPopularEntity: TvPopularEntity) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            mainRepository.insertTvFavorite(tvPopularEntity)
        }
    }

    fun deleteTvFavorite(tvPopularEntity: TvPopularEntity) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            mainRepository.deleteTvFavorite(tvPopularEntity)
        }
    }
}

