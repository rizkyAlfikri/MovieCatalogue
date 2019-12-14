package com.dicoding.picodiploma.moviecatalogue.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment and this feature is coming soon"
    }
    val text: LiveData<String> = _text
}