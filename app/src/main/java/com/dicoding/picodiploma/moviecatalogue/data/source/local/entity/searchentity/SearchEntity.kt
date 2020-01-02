package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_entity")
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var idSearch: Int,
    var mediaType: String
)