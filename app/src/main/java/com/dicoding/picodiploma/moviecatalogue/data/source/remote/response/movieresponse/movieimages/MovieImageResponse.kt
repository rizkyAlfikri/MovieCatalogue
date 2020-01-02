package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.movieimages


import com.google.gson.annotations.SerializedName

data class MovieImageResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("posters")
    var posters: List<MoviePosterResult>
)