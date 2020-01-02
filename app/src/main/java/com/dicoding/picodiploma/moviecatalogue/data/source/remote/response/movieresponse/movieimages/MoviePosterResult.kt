package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.movieimages


import com.google.gson.annotations.SerializedName

data class MoviePosterResult(
    @SerializedName("file_path")
    var filePath: String

)