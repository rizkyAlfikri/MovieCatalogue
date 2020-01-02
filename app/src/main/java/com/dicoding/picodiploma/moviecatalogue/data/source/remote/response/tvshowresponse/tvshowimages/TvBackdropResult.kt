package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowimages


import com.google.gson.annotations.SerializedName

data class TvBackdropResult(
    @SerializedName("file_path")
    var filePath: String

)