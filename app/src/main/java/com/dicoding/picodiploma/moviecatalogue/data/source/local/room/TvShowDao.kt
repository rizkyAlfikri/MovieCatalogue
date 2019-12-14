package com.dicoding.picodiploma.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvDetailEntity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tv_popular_entity")
    fun getPopularTv(): LiveData<List<TvPopularEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTvPopular(listTv: List<TvPopularEntity>)

    @Query("SELECT * FROM tv_detail_favorite WHERE id = :id")
    fun getDetailTvById(id: Int): LiveData<TvDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvDetailFavorite(tvDetailEntity: TvDetailEntity)

    @Delete
    fun deleteTvDetailFavorite(tvDetailEntity: TvDetailEntity)
}