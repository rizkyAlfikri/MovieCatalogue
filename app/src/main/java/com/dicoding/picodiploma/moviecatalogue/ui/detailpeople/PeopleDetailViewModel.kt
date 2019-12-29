package com.dicoding.picodiploma.moviecatalogue.ui.detailpeople

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository

class PeopleDetailViewModel(private val mainRepository: MainRepository): ViewModel() {

    private val idPeople = MutableLiveData<Int>()

    fun setIdPeople(id: Int) {
        idPeople.value = id
    }

    val getPeopleDetail = Transformations.switchMap(idPeople){
        mainRepository.getPeopleDetailData(it)
    }

}