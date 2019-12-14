package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail


import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResult
import com.google.gson.annotations.SerializedName

data class MovieDetailResult(
    @SerializedName("genres")
    var genres: List<MovieGenreResult>,
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("homepage")
    var homepage: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("poster_path")
    var posterPath: Any?,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("runtime")
    var runtime: Int,
    @SerializedName("status")
    var status: String,
    @SerializedName("tagline")
    var tagline: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("vote_average")
    var voteAverage: Double
)