package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviereviewentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_review_entity")
data class MovieReviewEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var idMovie: Int,
    var author: String,
    var content: String,
    var idReview: String,
    var url: String
)