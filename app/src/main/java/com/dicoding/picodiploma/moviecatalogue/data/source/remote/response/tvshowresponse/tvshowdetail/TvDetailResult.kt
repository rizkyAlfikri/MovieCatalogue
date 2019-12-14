package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowdetail


import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResult
import com.google.gson.annotations.SerializedName

data class TvDetailResult(
    @SerializedName("backdrop_path")
    var backdropPath: String,
    @SerializedName("first_air_date")
    var firstAirDate: String,
    @SerializedName("genres")
    var genres: List<TvGenreResult>,
    @SerializedName("homepage")
    var homepage: String,
    @SerializedName("episode_run_time")
    var episodeRunTime: List<Int>,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("vote_average")
    var voteAverage: Double


)