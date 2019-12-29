package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity

import androidx.room.Embedded
import androidx.room.Relation
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity

data class SearchWithMovieTvPeopleEntity(
    @Embedded
    var searchEntity: SearchEntity?,

    @Relation(
        parentColumn = "idSearch",
        entityColumn = "idMovie"
    )
    var listMovie: List<MoviePopularEntity>,

    @Relation(
        parentColumn = "idSearch",
        entityColumn = "idTv"
    )
    var listTv: List<TvPopularEntity>,

    @Relation(
        parentColumn = "idSearch",
        entityColumn = "idPeople"
    )
    var listPeople: List<PeopleEntity>

)