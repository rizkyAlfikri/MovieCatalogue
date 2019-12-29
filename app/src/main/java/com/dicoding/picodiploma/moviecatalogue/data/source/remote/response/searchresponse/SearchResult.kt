package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.searchresponse


import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("first_air_date")
    var firstAirDate: String,
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("id")
    var id: Int,
    @SerializedName("media_type")
    var mediaType: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("profile_path")
    var profilePath: String?,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("vote_average")
    var voteAverage: Double
)