package com.dicoding.picodiploma.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_popular_entity")
    fun getAllMoviePopular(): DataSource.Factory<Int, MoviePopularEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListMoviePopular(listMovie: List<MoviePopularEntity>)

    @Query("SELECT * FROM movie_detail WHERE id = :id")
    fun getMovieDetailById(id: Int): LiveData<MovieDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetailFavorite(movieDetailEntity: MovieDetailEntity)

    @Delete
    fun deleteMovieDetailFavorite(movieDetailEntity: MovieDetailEntity)

}