package com.dicoding.picodiploma.moviecatalogue.network

import com.dicoding.picodiploma.moviecatalogue.utils.Config
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okreplay.OkReplayInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val okReplayInterceptor = OkReplayInterceptor()
    private fun init(): Retrofit {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(okReplayInterceptor)
        return Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(builder.build())
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return init().create(service)
    }
}