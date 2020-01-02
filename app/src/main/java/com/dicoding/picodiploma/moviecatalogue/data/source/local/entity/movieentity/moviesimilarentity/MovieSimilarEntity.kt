package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviesimilarentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_similar_entity")
data class MovieSimilarEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "idMovie")
    var idMovie: Int,
    @ColumnInfo(name = "image_path")
    var imagePath: String?,
    @ColumnInfo(name = "title")
    var title: String?
)