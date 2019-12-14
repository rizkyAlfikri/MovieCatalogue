package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviepopular


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviePopularResult(
    @SerializedName("id")
    var id: Int,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("vote_average")
    var voteAverage: Double
) : Parcelable