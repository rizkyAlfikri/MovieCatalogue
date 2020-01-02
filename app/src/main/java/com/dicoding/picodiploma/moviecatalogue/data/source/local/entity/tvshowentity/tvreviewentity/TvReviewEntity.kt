package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvreviewentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_review_entity")
data class TvReviewEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var idTv: Int,
    var author: String,
    var content: String,
    var idReview: String,
    var url: String
)