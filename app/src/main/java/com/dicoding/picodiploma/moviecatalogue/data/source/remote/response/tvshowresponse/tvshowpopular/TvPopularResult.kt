package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular


import com.google.gson.annotations.SerializedName

data class TvPopularResult(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("first_air_date")
    var firstAirDate: String
)