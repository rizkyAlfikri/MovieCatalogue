package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre


import com.google.gson.annotations.SerializedName

data class TvGenreResponse(
    @SerializedName("genres")
    var genres: List<TvGenreResult>
)