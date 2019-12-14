package com.dicoding.picodiploma.moviecatalogue.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.MovieResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail.MovieDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.TvResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.network.ApiService
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.NullPointerException

class RemoteRepository private constructor(
    private val apiService: ApiService,
    private val apiKey: String
) {

    private val exception = CoroutineExceptionHandler { _, throwable ->
        Log.e(RemoteRepository::class.java.simpleName, "${throwable.message}")
    }

    companion object {
        private var INSTANCE: RemoteRepository? = null

        fun getInstance(apiService: ApiService, apiKey: String): RemoteRepository? {
            if (INSTANCE == null) {
                INSTANCE =
                    RemoteRepository(
                        apiService,
                        apiKey
                    )
            }

            return INSTANCE
        }
    }

    fun getPopularMovieRepo(): MutableLiveData<ApiResponse<MovieResultWithGenre>> {
        val resultMoviePopular = MutableLiveData<ApiResponse<MovieResultWithGenre>>()

        CoroutineScope(Dispatchers.IO + exception).launch {

            try {
                val listPopularMovie = async { apiService.getMoviePopularApi(apiKey) }
                val listGenreMovie = async { apiService.getMovieGenreApi(apiKey) }

                val movieWithGenre =
                    MovieResultWithGenre(
                        listPopularMovie.await().results,
                        listGenreMovie.await().genres
                    )
                resultMoviePopular.postValue(ApiResponse.success(movieWithGenre))

            } catch (e: Exception) {
                resultMoviePopular.postValue(ApiResponse.error("${e.message}", null))
            } catch (e: NullPointerException) {
                resultMoviePopular.postValue(ApiResponse.empty("${e.message}", null))
            }
        }

        return resultMoviePopular
    }

    fun getDetailMovieRepo(idMovie: Int): LiveData<ApiResponse<MovieDetailResult>> {
        val resultMovieDetail = MutableLiveData<ApiResponse<MovieDetailResult>>()
        CoroutineScope(Dispatchers.IO + exception).launch {

            try {
                resultMovieDetail.postValue(
                    ApiResponse.success(apiService.getMovieDetailApi(idMovie, apiKey))
                )
            } catch (e: Exception) {
                ApiResponse.error("${e.message}", null)
            } catch (e: NullPointerException) {
                ApiResponse.empty("${e.message}", null)
            }
        }

        return resultMovieDetail
    }


    fun getPopularTvRepo(): LiveData<ApiResponse<TvResultWithGenre>> {
        val resultTvPopular = MutableLiveData<ApiResponse<TvResultWithGenre>>()

        CoroutineScope(Dispatchers.IO + exception).launch {
            val tvPopularResult = async { apiService.getTvPopularApi(apiKey) }
            val tvGenreResult = async { apiService.getTvGenreApi(apiKey) }

            val tvResultWithGenre =
                TvResultWithGenre(tvPopularResult.await().results, tvGenreResult.await().genres)

            try {
                resultTvPopular.postValue(ApiResponse.success(tvResultWithGenre))
            } catch (e: Exception) {
                resultTvPopular.postValue(ApiResponse.error("${e.message}", null))
            } catch (e: NullPointerException) {
                resultTvPopular.postValue(ApiResponse.error("${e.message}", null))
            }
        }

        return resultTvPopular

    }

    suspend fun getGenreTvRepo() = apiService.getTvGenreApi(apiKey)

    suspend fun getDetailTvRepo(idTv: Int) = apiService.getTvDetailApi(idTv, apiKey)


}