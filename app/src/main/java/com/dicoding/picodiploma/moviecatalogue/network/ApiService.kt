package com.dicoding.picodiploma.moviecatalogue.network

import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail.MovieDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviepopular.MovieResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowdetail.TvDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getMoviePopularApi(@Query("api_key") apiKey: String): MovieResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenreApi(@Query("api_key") apiKey: String): MovieGenreResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailApi(@Path("movie_id") idMovie: Int, @Query("api_key") apiKey: String): MovieDetailResult


    @GET("tv/popular")
    suspend fun getTvPopularApi(@Query("api_key") apiKey: String): TvPopularResponse

    @GET("tv/{tv_id}")
    suspend fun getTvDetailApi(@Path("tv_id") idTv: Int, @Query("api_key") apiKey: String): TvDetailResult

    @GET("genre/movie/list")
    suspend fun getTvGenreApi(@Query("api_key") apiKey: String): TvGenreResponse
}