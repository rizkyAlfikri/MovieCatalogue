package com.dicoding.picodiploma.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.TvDetailWithInfoEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvpopularentity.TvPopularEntity

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tv_popular_entity")
    fun getPopularTv(): LiveData<List<TvPopularEntity>>

    @Query("SELECT * FROM tv_popular_entity ORDER by num DESC")
    fun getAllTvShowFavorite(): DataSource.Factory<Int, TvPopularEntity>

    @Transaction
    @Query("SELECT * FROM tv_detail_favorite WHERE id = :id")
    fun getDetailTvById(id: Int): LiveData<TvDetailWithInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvFavorite(tvPopularEntity: TvPopularEntity)

    @Delete
    fun deleteTvFavorite(tvPopularEntity: TvPopularEntity)

    @Query("DELETE FROM tv_popular_entity WHERE idTv = :tvId")
    fun deleteTvFavoriteById(tvId: Int)

    @Query("SELECT * FROM tv_popular_entity WHERE idTv = :id")
    fun getTvFavoriteById(id: Int): LiveData<TvPopularEntity>
}