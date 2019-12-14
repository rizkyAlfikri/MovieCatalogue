package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular


import com.google.gson.annotations.SerializedName

data class TvPopularResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<TvPopularResult>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)