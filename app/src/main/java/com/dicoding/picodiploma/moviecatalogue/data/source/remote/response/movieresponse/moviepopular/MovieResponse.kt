package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviepopular


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<MoviePopularResult>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)