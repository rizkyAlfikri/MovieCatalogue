package com.dicoding.picodiploma.moviecatalogue.di

import android.app.Application
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.LocalRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.room.MovieCatalogDatabase
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.RemoteRepository
import com.dicoding.picodiploma.moviecatalogue.network.ApiService
import com.dicoding.picodiploma.moviecatalogue.network.RetrofitService
import com.dicoding.picodiploma.moviecatalogue.utils.Config

class Injection {

    companion object {
        private const val imagePath = Config.IMAGE_URL_BASE_PATH
        private const val apiKey = Config.API_KEY
        private val apiService = RetrofitService.createService(ApiService::class.java)


        fun provideRepositoryResource(application: Application): MainRepository? {
            val database = MovieCatalogDatabase.getInstance(application)
            val local = LocalRepository.getInstance(database.movieDao(), database.tvShowDao())
            val remote = RemoteRepository.getInstance(apiService, apiKey)

            return remote?.let { MainRepository.getInstance(it, local, imagePath) }
        }
    }
}