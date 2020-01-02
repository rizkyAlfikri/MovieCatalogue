package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.movievideos


import com.google.gson.annotations.SerializedName

data class MovieVideoResonse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("results")
    var results: List<MovieVideoResult>
)