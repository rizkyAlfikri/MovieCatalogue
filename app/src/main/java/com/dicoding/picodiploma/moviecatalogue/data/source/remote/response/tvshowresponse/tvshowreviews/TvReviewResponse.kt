package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowreviews


import com.google.gson.annotations.SerializedName

data class TvReviewResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<TvReviewResult>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)