package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvsimilarentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_similar_entity")
data class TvSimilarEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var idTv: Int,
    var title: String?,
    var imagePath: String?
)