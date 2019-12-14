package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre


import com.google.gson.annotations.SerializedName

data class MovieGenreResponse(
    @SerializedName("genres")
    var genres: List<MovieGenreResult>
)