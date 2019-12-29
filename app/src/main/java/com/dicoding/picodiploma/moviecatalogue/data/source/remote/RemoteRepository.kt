package com.dicoding.picodiploma.moviecatalogue.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.MovieResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail.MovieDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.peopleresponse.PeopleDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.searchresponse.SearchWithGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.TvResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowdetail.TvDetailResult
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

    fun getPopularMovieRepo(sortBy: String): MutableLiveData<ApiResponse<MovieResultWithGenre>> {
        val resultMoviePopular = MutableLiveData<ApiResponse<MovieResultWithGenre>>()

        CoroutineScope(Dispatchers.IO + exception).launch {

            try {
                val listPopularMovie = async { apiService.getMoviePopularApi(sortBy, apiKey) }
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
                val movieDetailResult = apiService.getMovieDetailApi(idMovie, apiKey)

                resultMovieDetail.postValue(
                    ApiResponse.success(movieDetailResult)
                )
            } catch (e: Exception) {
                ApiResponse.error("${e.message}", null)
            } catch (e: NullPointerException) {
                ApiResponse.empty("${e.message}", null)
            }
        }

        return resultMovieDetail
    }


    fun getPopularTvRepo(sortBy: String): LiveData<ApiResponse<TvResultWithGenre>> {
        val resultTvPopular = MutableLiveData<ApiResponse<TvResultWithGenre>>()

        CoroutineScope(Dispatchers.IO + exception).launch {

            try {
                val tvPopularResult = async { apiService.getTvPopularApi(sortBy, apiKey) }
                val tvGenreResult = async { apiService.getTvGenreApi(apiKey) }

                val tvResultWithGenre =
                    TvResultWithGenre(tvPopularResult.await().results, tvGenreResult.await().genres)
                resultTvPopular.postValue(ApiResponse.success(tvResultWithGenre))
            } catch (e: Exception) {
                resultTvPopular.postValue(ApiResponse.error("${e.message}", null))
            } catch (e: NullPointerException) {
                resultTvPopular.postValue(ApiResponse.error("${e.message}", null))
            }
        }

        return resultTvPopular

    }

    fun getDetailTvRepo(idTv: Int): LiveData<ApiResponse<TvDetailResult>> {
        val resultTvDetail = MutableLiveData<ApiResponse<TvDetailResult>>()

        CoroutineScope(Dispatchers.IO + exception).launch {
            val tvDetailResult = apiService.getTvDetailApi(idTv, apiKey)

            try {
                resultTvDetail.postValue(ApiResponse.success(tvDetailResult))
            } catch (e: Exception) {
                resultTvDetail.postValue(ApiResponse.error("${e.message}", null))
            } catch (e: NullPointerException) {
                resultTvDetail.postValue(ApiResponse.empty("${e.message}", null))
            }
        }

        return resultTvDetail
    }

    fun getSearchRepo(query: String): LiveData<ApiResponse<SearchWithGenreResult>> {
        val resultSearch = MutableLiveData<ApiResponse<SearchWithGenreResult>>()

        CoroutineScope(Dispatchers.IO + exception).launch {
            val searchData = async { apiService.getSearchApi(query, apiKey) }
            val movieGenreData = async { apiService.getMovieGenreApi(apiKey) }
            val tvGenreData = async { apiService.getTvGenreApi(apiKey) }
            try {
                resultSearch.postValue(
                    ApiResponse.success(
                        SearchWithGenreResult(
                            searchData.await().results,
                            movieGenreData.await().genres,
                            tvGenreData.await().genres
                        )
                    )
                )
            } catch (e: Exception) {
                resultSearch.postValue(ApiResponse.error("${e.message}", null))
            } catch (e: NullPointerException) {
                resultSearch.postValue(ApiResponse.empty("${e.message}", null))
            }
        }

        return resultSearch
    }

    fun getDetailPersonRepo(idPerson: Int): LiveData<ApiResponse<PeopleDetailResult>> {
        val resultDetailPeople = MutableLiveData<ApiResponse<PeopleDetailResult>>()

        CoroutineScope(Dispatchers.IO + exception).launch {
            val peopleDetail = apiService.getPeopleDetail(idPerson, apiKey)

            try {
                resultDetailPeople.postValue(ApiResponse.success(peopleDetail))
            } catch (e: Exception) {
                resultDetailPeople.postValue(ApiResponse.error("${e.message}", null))
            } catch (e: NullPointerException) {
                resultDetailPeople.postValue(ApiResponse.empty("${e.message}", null))
            }
        }

        return resultDetailPeople

    }
}