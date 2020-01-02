package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowvideos


import com.google.gson.annotations.SerializedName

data class TvVideosResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("results")
    var results: List<TvVideosResult>
)