package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvdetailentity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tv_detail_favorite")
data class TvDetailEntity(
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
    var title: String,
    var voteAverage: Double,
    var keyVideo: String?
)