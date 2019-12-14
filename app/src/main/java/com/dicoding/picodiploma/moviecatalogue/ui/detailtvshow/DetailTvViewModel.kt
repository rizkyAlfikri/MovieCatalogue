package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvDetailEntity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.utils.CoroutineContextProvider
import com.dicoding.picodiploma.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class DetailTvViewModel(
    private val mainRepository: MainRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : ViewModel() {

    private var idTv = 0
    private val tvDetailData = MutableLiveData<TvDetailEntity>()
    private val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        Log.e(DetailTvViewModel::class.java.name, "${throwable.message}")
    }

    fun setTvId(id: Int) {
        idTv = id
    }

    fun getTvDetail(): MutableLiveData<TvDetailEntity> {
        EspressoIdlingResource.idlingIncrement()

        viewModelScope.launch(context.main + handler) {
            tvDetailData.postValue(mainRepository.getDetailTvData(idTv))
        }

        EspressoIdlingResource.idlingDecrement()
        return tvDetailData
    }
}