package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowvideos


import com.google.gson.annotations.SerializedName

data class TvVideosResult(
    @SerializedName("key")
    var key: String
)