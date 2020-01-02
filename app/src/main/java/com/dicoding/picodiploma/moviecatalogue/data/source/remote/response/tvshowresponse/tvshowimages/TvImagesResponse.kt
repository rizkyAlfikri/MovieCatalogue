package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowimages


import com.google.gson.annotations.SerializedName

data class TvImagesResponse(
    @SerializedName("backdrops")
    var backdrops: List<TvBackdropResult>,
    @SerializedName("id")
    var id: Int
)