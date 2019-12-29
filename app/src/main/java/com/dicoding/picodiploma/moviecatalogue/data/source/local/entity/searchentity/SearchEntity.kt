package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity

import androidx.room.*
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity

@Entity(tableName = "search_entity")
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var idSearch: Int,
    var mediaType: String
)