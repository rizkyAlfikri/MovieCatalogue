package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowreviews


import com.google.gson.annotations.SerializedName

data class TvReviewResult(
    @SerializedName("author")
    var author: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("url")
    var url: String
)