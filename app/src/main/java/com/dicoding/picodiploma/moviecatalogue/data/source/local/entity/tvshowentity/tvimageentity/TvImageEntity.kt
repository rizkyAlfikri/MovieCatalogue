package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvimageentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_image_entity")
data class TvImageEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var idTv: Int,
    var imagePath: String
)