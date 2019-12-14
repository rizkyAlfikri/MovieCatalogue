package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_popular_entity")
data class MoviePopularEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "image_path")
    var imagePath: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "genre")
    var genre: String,
    @ColumnInfo(name = "release_date")
    var releaseDate: String,
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double
)