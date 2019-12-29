package com.dicoding.picodiploma.moviecatalogue.network

import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail.MovieDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviepopular.MovieResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.peopleresponse.PeopleDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.searchresponse.SearchResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowdetail.TvDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/{sort_by}")
    suspend fun getMoviePopularApi(@Path("sort_by") sortBy: String, @Query("api_key") apiKey: String): MovieResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenreApi(@Query("api_key") apiKey: String): MovieGenreResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailApi(@Path("movie_id") idMovie: Int, @Query("api_key") apiKey: String): MovieDetailResult


    @GET("tv/{sort_by}")
    suspend fun getTvPopularApi(@Path("sort_by") sortBy: String, @Query("api_key") apiKey: String): TvPopularResponse

    @GET("tv/{tv_id}")
    suspend fun getTvDetailApi(@Path("tv_id") idTv: Int, @Query("api_key") apiKey: String): TvDetailResult

    @GET("genre/movie/list")
    suspend fun getTvGenreApi(@Query("api_key") apiKey: String): TvGenreResponse


    @GET("search/multi")
    suspend fun getSearchApi(@Query("query") query: String, @Query("api_key") apuKey: String): SearchResponse

    @GET("person/{person_id}")
    suspend fun getPeopleDetail(@Path("person_id") idPerson: Int, @Query("api_key") apiKey: String): PeopleDetailResult
}