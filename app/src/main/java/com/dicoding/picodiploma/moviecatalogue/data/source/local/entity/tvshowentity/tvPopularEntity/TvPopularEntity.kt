package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_popular_entity")
data class TvPopularEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "num")
    var id: Int?,
    var idTv: Int,
    var title: String?,
    var imagePath: String?,
    var genreIds: String,
    var voteAverage: Double?,
    var release: String?,
    var isFavorite: Boolean
)