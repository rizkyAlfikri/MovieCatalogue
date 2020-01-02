package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail")
data class MovieDetailEntity(
    var genres: String,
    var backdropPath: String,
    var homepage: String? = " - ",
    @PrimaryKey
    var id: Int,
    var overview: String? = " - ",
    var imagePath: String?,
    var releaseDate: String,
    var runtime: Int = 0,
    var status: String,
    var tagLine: String? = " - ",
    var title: String,
    var voteAverage: Double,
    var keyVideo: String?
)