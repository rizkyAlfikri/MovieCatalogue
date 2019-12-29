package com.dicoding.picodiploma.moviecatalogue.viewmodels

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.moviecatalogue.MainViewModel
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleDetailEntity
import com.dicoding.picodiploma.moviecatalogue.di.Injection
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieViewModel
import com.dicoding.picodiploma.moviecatalogue.ui.detailpeople.PeopleDetailViewModel
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvViewModel
import com.dicoding.picodiploma.moviecatalogue.ui.favorite.FavoriteViewModel
import com.dicoding.picodiploma.moviecatalogue.ui.search.SearchViewModel

class ViewModelFactory private constructor(private val mainRepository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            Injection.provideRepositoryResource(application)
                                ?.let { ViewModelFactory(it) }
                    }
                }
            }

            return INSTANCE
        }
    }

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(mainRepository) as T

            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> DetailMovieViewModel(
                mainRepository
            ) as T

            modelClass.isAssignableFrom(DetailTvViewModel::class.java) -> DetailTvViewModel(
                mainRepository
            ) as T

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(
                mainRepository
            ) as T

            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(
                mainRepository
            ) as T

            modelClass.isAssignableFrom(PeopleDetailViewModel::class.java) -> PeopleDetailViewModel(
                mainRepository
            ) as T

            else -> throw IllegalStateException("Unknown ViewModel Class ${modelClass.name}")
        }
    }
}