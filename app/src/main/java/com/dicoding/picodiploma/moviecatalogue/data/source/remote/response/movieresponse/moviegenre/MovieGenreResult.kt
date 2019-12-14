package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre


import com.google.gson.annotations.SerializedName

data class MovieGenreResult(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String
)