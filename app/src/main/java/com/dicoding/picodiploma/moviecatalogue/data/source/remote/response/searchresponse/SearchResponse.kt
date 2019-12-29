package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.searchresponse


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: List<SearchResult>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)