package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviereviews


import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<MovieReviewResult>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)