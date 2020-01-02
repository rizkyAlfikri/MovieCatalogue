package com.dicoding.picodiploma.moviecatalogue

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.ApiResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.RemoteRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.MovieDetailWithInfoResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.MovieResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.peopleresponse.PeopleDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.searchresponse.SearchWithGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.TvDetailWithInfoResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.TvResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.network.ApiService
import kotlinx.coroutines.*

class FakeRemoteRepository (private val apiService: ApiService, private val apiKey: String) {

    private val exception = CoroutineExceptionHandler { _, throwable ->
        Log.e(RemoteRepository::class.java.simpleName, "${throwable.message}")
    }

    companion object{
        private var INSTANCE: FakeRemoteRepository? = null

        fun getInstance(apiService: ApiService, apiKey: String): FakeRemoteRepository? {
            if (INSTANCE == null) {
                INSTANCE = FakeRemoteRepository(apiService, apiKey)
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

    fun getDetailMovieRepo(idMovie: Int): LiveData<ApiResponse<MovieDetailWithInfoResult>> {
        val resultMovieDetail = MutableLiveData<ApiResponse<MovieDetailWithInfoResult>>()
        CoroutineScope(Dispatchers.IO + exception).launch {

            try {
                val movieDetailResult = async { apiService.getMovieDetailApi(idMovie, apiKey) }
                val movieVideoResult = async { apiService.getMovieTrailerApi(idMovie, apiKey) }
                val movieReviewResult = async { apiService.getMovieReviewApi(idMovie, apiKey) }
                val movieSimilarResult = async { apiService.getMovieSimilarApi(idMovie, apiKey) }
                val movieImagesResult = async { apiService.getMovieImageApi(idMovie, apiKey) }
                movieDetailResult.await().keyVideo = movieVideoResult.await().results.first().key

                resultMovieDetail.postValue(
                    ApiResponse.success(
                        MovieDetailWithInfoResult(
                            movieDetailResult.await(),
                            movieReviewResult.await(),
                            movieImagesResult.await(),
                            movieSimilarResult.await().results
                        )
                    )
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

    fun getDetailTvRepo(idTv: Int): LiveData<ApiResponse<TvDetailWithInfoResult>> {
        val resultTvDetail = MutableLiveData<ApiResponse<TvDetailWithInfoResult>>()

        CoroutineScope(Dispatchers.IO + exception).launch {
            val tvDetailResult = async { apiService.getTvDetailApi(idTv, apiKey) }
            val tvVideoResult = async { apiService.getTvVideoApi(idTv, apiKey) }
            val tvSimilarResult = async { apiService.getTvSimilarApi(idTv, apiKey) }
            val tvReviewResult = async { apiService.getTvReviewApi(idTv, apiKey) }
            val tvImageResult = async { apiService.getTvImageApi(idTv, apiKey) }

            tvDetailResult.await().keyVideo = tvVideoResult.await().results.first().key

            try {
                resultTvDetail.postValue(
                    ApiResponse.success(
                        TvDetailWithInfoResult(
                            tvDetailResult.await(),
                            tvReviewResult.await(),
                            tvImageResult.await(),
                            tvSimilarResult.await().results
                        )
                    )
                )
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