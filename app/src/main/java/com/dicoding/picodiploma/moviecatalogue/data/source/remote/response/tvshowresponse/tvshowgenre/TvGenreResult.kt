package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre


import com.google.gson.annotations.SerializedName

data class TvGenreResult(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String
)