package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity

import androidx.room.Embedded
import androidx.room.Relation
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvdetailentity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvimageentity.TvImageEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvreviewentity.TvReviewEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvsimilarentity.TvSimilarEntity

data class TvDetailWithInfoEntity (
    @Embedded
    var tvDetailEntity: TvDetailEntity,

    @Relation(parentColumn = "id", entityColumn = "idTv" )
    var listTvImage: List<TvImageEntity>,

    @Relation(parentColumn = "id", entityColumn = "idTv")
    var listTvReview: List<TvReviewEntity>,

    @Relation(parentColumn = "id", entityColumn = "idTv")
    var listTvSimilar: List<TvSimilarEntity>
)