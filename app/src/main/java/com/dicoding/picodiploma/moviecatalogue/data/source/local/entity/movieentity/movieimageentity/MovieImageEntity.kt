package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.movieimageentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_image_entity")
data class MovieImageEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var idMovie: Int,
    var imagePath: String
)