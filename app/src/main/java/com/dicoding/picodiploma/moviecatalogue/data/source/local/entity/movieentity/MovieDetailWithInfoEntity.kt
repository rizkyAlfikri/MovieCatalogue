package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity

import androidx.room.Embedded
import androidx.room.Relation
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.movieimageentity.MovieImageEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviereviewentity.MovieReviewEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviesimilarentity.MovieSimilarEntity

data class MovieDetailWithInfoEntity(
    @Embedded
    val movieDetailEntity: MovieDetailEntity,

    @Relation(parentColumn = "id", entityColumn = "idMovie")
    var listMovieReview: List<MovieReviewEntity>,

    @Relation(parentColumn = "id", entityColumn = "idMovie")
    var listMovieImage: List<MovieImageEntity>,

    @Relation(parentColumn = "id", entityColumn = "idMovie")
    var listMovieSimilar: List<MovieSimilarEntity>
)